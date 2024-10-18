package coupon.coupon.dto;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponCreateRequest(
        String name,
        BigDecimal discountAmount,
        BigDecimal minimumOrderAmount,
        String category,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {

    public Coupon toCouponEntity() {
        return new Coupon(name, discountAmount, minimumOrderAmount, Category.valueOf(category), issuedAt, expiredAt);
    }
}
