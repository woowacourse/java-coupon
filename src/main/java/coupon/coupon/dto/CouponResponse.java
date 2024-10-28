package coupon.coupon.dto;

import coupon.coupon.domain.Category;
import coupon.coupon.entity.CouponEntity;

import java.time.LocalDate;

public record CouponResponse(
        Long id,
        String name,
        int discountAmount,
        int minimumOrderAmount,
        Category category,
        LocalDate startDate,
        LocalDate endDate
) {

    public static CouponResponse from(CouponEntity coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(),
                coupon.getCategory(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }
}
