package coupon.dto;

import coupon.entity.Category;
import java.time.LocalDate;

public record CouponCreateRequest(
        String name,
        int discount,
        int minimumOrder,
        Category category,
        LocalDate start,
        LocalDate end
) {
}
