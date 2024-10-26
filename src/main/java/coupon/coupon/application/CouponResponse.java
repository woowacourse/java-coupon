package coupon.coupon.application;

import java.time.LocalDateTime;

public record CouponResponse(
        Long id,
        String name,
        Long discountAmount,
        Long minOrderAmount,
        String category,
        LocalDateTime issueStartDate,
        LocalDateTime issueEndDate
) {
}
