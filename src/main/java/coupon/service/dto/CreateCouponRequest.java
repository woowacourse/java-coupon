package coupon.service.dto;

import java.time.LocalDateTime;

public record CreateCouponRequest(
        String couponName,
        long discountAmount,
        long minimumOrderAmount,
        LocalDateTime startDate,
        LocalDateTime expirationDate
) {
}
