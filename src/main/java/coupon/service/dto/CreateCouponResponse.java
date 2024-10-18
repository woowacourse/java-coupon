package coupon.service.dto;

import java.time.LocalDateTime;

import coupon.domain.Coupon;

public record CreateCouponResponse(
        String couponName,
        long discountAmount,
        long discountRate,
        long minimumOrderAmount,
        LocalDateTime startDate,
        LocalDateTime expirationDate
) {

    public static CreateCouponResponse from(final Coupon coupon) {
        return new CreateCouponResponse(
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getDiscountRate().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getValidityPeriod().getStartDate(),
                coupon.getValidityPeriod().getExpirationDate()
        );
    }
}
