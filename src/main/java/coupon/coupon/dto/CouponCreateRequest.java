package coupon.coupon.dto;

import coupon.coupon.domain.Category;

import java.time.LocalDate;

public record CouponCreateRequest(String name, int discountAmount, int minimumOrderAmount, Category category,
                                  LocalDate startDate, LocalDate endDate) {
}
