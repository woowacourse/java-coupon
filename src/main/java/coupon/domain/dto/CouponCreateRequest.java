package coupon.domain.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;

import java.time.LocalDate;

public record CouponCreateRequest(String name, Integer discountAmount, Integer minOrderAmount,
                                  Category category, LocalDate startDate, LocalDate endDate) {

    public Coupon toEntity() {
        return new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);
    }
}
