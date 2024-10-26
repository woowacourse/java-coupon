package coupon.coupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;

public record MemberCouponResponse(
        Long memberCouponId,
        String couponName,
        String category,
        int discountAmount,
        int minOrderAmount,
        boolean isUsed,
        String issuedAt,
        String expiredAt
) {

    public static MemberCouponResponse from(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                coupon.getName(),
                coupon.getCategory().getCategory(),
                coupon.getDiscountAmount(),
                coupon.getMinOrderAmount(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt().toString(),
                memberCoupon.getExpiredAt().toString()
        );
    }
}
