package coupon.coupon.dto;

import coupon.coupon.domain.Coupon;

import java.time.LocalDate;

public record CouponRequest(
        String name,
        int discountAmount,
        int minOrderAmount,
        String category,
        LocalDate startDate,
        LocalDate endDate) {

    public Coupon toEntity() {
        return new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);
    }
}
