package coupon.coupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.Period;

import java.time.LocalDate;

public record CouponResponse(
        long id,
        String name,
        int discountAmount,
        int minOrderAmount,
        String category,
        LocalDate startDate,
        LocalDate endDate) {

    public static CouponResponse from(Coupon coupon) {
        Period period = coupon.getPeriod();

        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinOrderAmount(),
                coupon.getCategory(),
                period.getStartDate(),
                period.getEndDate()
        );
    }
}
