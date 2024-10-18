package coupon.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import coupon.domain.Coupon;

public record GetCouponResponse(
        String couponName,
        long discountAmount,
        long minimumOrderAmount,
        long discountRate,
        LocalDateTime startDate,
        LocalDateTime expirationDate
) {

    public static GetCouponResponse from(final Coupon coupon) {
        return new GetCouponResponse(
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getDiscountRate().getValue(),
                coupon.getValidityPeriod().getStartDate(),
                coupon.getValidityPeriod().getExpirationDate()
        );
    }
}
