package coupon.coupon.dto;

import coupon.coupon.domain.MemberCoupon;

public record MemberCouponResponse(
        Long id,
        Long couponResponse,
        boolean used
) {
    public static MemberCouponResponse from(final MemberCoupon memberCoupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getCouponId(),
                memberCoupon.isUsed()
        );
    }
}
