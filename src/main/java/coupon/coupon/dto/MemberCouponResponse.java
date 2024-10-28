package coupon.coupon.dto;

import coupon.coupon.domain.MemberCoupon;

public record MemberCouponResponse(
        Long id,
        CouponResponse couponResponse,
        boolean used
) {
    public static MemberCouponResponse from(final MemberCoupon memberCoupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                CouponResponse.from(memberCoupon.getCoupon()),
                memberCoupon.isUsed()
        );
    }
}
