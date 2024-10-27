package coupon.coupon.controller.dto;

import java.time.LocalDateTime;

import coupon.coupon.domain.Coupon;

public record CouponResponse(
        Long id,
        String name,
        Long discountAmount,
        Long minimumOrderAmount,
        String category,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    public CouponResponse(Coupon coupon) {
        this(
                coupon.getId(),
                coupon.getName().getName(),
                coupon.getDiscountAmount().getDiscountAmount(),
                coupon.getMinimumOrderAmount().getMinimumOrderAmount(),
                coupon.getCategory().name(),
                coupon.getIssuancePeriod().getStartDate(),
                coupon.getIssuancePeriod().getEndDate()
        );
    }
}
