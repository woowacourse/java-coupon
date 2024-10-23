package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import java.time.LocalDate;

public record CouponResponse(
        Long id,
        String name,
        int discountAmount,
        int minOrderAmount,
        CouponCategory category,
        LocalDate startDate,
        LocalDate endDate
) {
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinOrderAmount(),
                coupon.getCategory(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }
}
