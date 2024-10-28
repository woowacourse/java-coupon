package coupon.application.coupon;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import coupon.application.membercoupon.IssuedCouponResponse;
import coupon.application.membercoupon.MemberCouponMapper;
import coupon.application.membercoupon.MemberCouponResponse;
import coupon.domain.Coupon;
import coupon.domain.CouponDiscountApply;
import coupon.domain.CouponIssuableDuration;
import coupon.domain.CouponRepository;
import coupon.domain.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MemberCouponMapperImpl implements MemberCouponMapper {

    private final CouponRepository couponRepository;

    @Override
    public List<MemberCouponResponse> map(List<MemberCoupon> memberCoupons) {
        List<Coupon> coupons = findCoupons(memberCoupons);
        Map<Long, IssuedCouponResponse> issuedCouponMap = createIssuedCouponMap(coupons);

        return memberCoupons.stream()
                .map(it -> assemble(issuedCouponMap, it))
                .toList();
    }

    private List<Coupon> findCoupons(List<MemberCoupon> memberCoupons) {
        Set<Long> ids = memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .collect(Collectors.toSet());

        return couponRepository.findByIdIn(ids);
    }

    private Map<Long, IssuedCouponResponse> createIssuedCouponMap(List<Coupon> coupons) {
        return coupons.stream()
                .map(this::toMiddleObject)
                .collect(Collectors.toMap(IssuedCouponResponse::id, Function.identity()));
    }

    private IssuedCouponResponse toMiddleObject(Coupon coupon) {
        CouponIssuableDuration expiryDuration = coupon.getExpiryDuration();
        CouponDiscountApply discountApply = coupon.getDiscountApply();

        return new IssuedCouponResponse(
                coupon.getId(),
                coupon.getName().value(),
                coupon.getCategory().name().toLowerCase(),
                expiryDuration.getStart(),
                expiryDuration.getEnd(),
                discountApply.getDiscountAmount().value(),
                discountApply.getApplicableAmount().value()
        );
    }

    private MemberCouponResponse assemble(Map<Long, IssuedCouponResponse> issuedCouponMap, MemberCoupon memberCoupon) {
        IssuedCouponResponse issuedCoupons = issuedCouponMap.get(memberCoupon.getCouponId());

        return new MemberCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getMemberId(),
                issuedCoupons,
                memberCoupon.getIsUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt()
        );
    }
}
