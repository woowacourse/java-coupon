package coupon.coupon.application;

import java.time.LocalDate;

public record CreateCouponRequest(
        String name,
        Long discountAmount,
        Long minOrderAmount,
        String category,
        LocalDate issueStartDate,
        LocalDate issueEndDate
) {
}
