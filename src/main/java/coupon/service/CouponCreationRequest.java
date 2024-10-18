package coupon.service;

import coupon.domain.Category;
import java.time.LocalDate;

public record CouponCreationRequest(
        String name,
        Category category,
        long discountPrice,
        long minimumOrderPrice,
        LocalDate issuableDate,
        LocalDate expirationDate
) {
}
