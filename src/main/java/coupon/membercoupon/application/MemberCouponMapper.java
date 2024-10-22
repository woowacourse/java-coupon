package coupon.membercoupon.application;

import java.util.List;
import java.util.Map;
import coupon.coupon.application.CouponMapper;
import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponMapper {

    private final CouponMapper couponMapper;

    public List<MemberCouponWithCouponResponse> toWithCouponResponses(
            List<MemberCoupon> memberCoupons,
            Map<Long, Coupon> couponMap
    ) {
        return memberCoupons.stream()
                .map(memberCoupon -> {
                    Coupon coupon = couponMap.get(memberCoupon.getCouponId());
                    return toWithCouponResponse(memberCoupon, coupon);
                })
                .toList();
    }

    public MemberCouponWithCouponResponse toWithCouponResponse(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponWithCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt(),
                couponMapper.toResponse(coupon)
        );
    }
}
