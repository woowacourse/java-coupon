package coupon.application.coupon;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import coupon.DataSourceRoutingSupport;
import coupon.application.membercoupon.IssuedCouponResponse;
import coupon.application.membercoupon.MemberCouponMapper;
import coupon.application.membercoupon.MemberCouponResponse;
import coupon.domain.Coupon;
import coupon.domain.CouponDiscountApply;
import coupon.domain.CouponIssuableDuration;
import coupon.domain.MemberCoupon;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MemberCouponMapperImpl implements MemberCouponMapper {

    private final CouponService couponService;
    private final DataSourceRoutingSupport routingSupport;

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

        return ids.stream()
                .map(this::getFromReadOrElseWrite)
                .toList();
    }

    private Coupon getFromReadOrElseWrite(Long id) {
        Supplier<Coupon> couponSupplier = getCouponSupplier(id);
        try {
            return couponSupplier.get();
        } catch (EntityNotFoundException e) {
            return routingSupport.changeToWrite(couponSupplier);
        }
    }

    private Supplier<Coupon> getCouponSupplier(Long couponId) {
        return () -> couponService.getCoupon(couponId);
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
