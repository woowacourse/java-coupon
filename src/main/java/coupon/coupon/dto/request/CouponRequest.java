package coupon.coupon.dto.request;

import java.time.LocalDateTime;

import coupon.coupon.domain.Category;

public record CouponRequest(
        String name,
        Integer discountPrice,
        Integer minimumOrderPrice,
        Category category,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
}
