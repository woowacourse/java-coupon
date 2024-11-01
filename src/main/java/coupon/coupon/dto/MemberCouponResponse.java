package coupon.coupon.dto;

import coupon.coupon.domain.CouponEntity;
import coupon.coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(

        String couponName,
        int couponDiscountAmount,
        int couponMinOrderAmount,
        String couponCategory,
        LocalDateTime couponStartDate,
        LocalDateTime couponEndDate,
        boolean used
) {
    public static MemberCouponResponse fromDomain(CouponEntity coupon, MemberCoupon memberCoupon) {
        return new MemberCouponResponse(
                coupon.getCouponName(),
                coupon.getCouponDiscountAmount(),
                coupon.getCouponMinOrderAmount(),
                coupon.getCouponCategory().name(),
                coupon.getStartAt(),
                coupon.getEndAt(),
                memberCoupon.isUsed()
        );
    }
}
