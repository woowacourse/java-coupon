package coupon.coupon.dto;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(
        Long id,
        String name,
        BigDecimal discountAmount,
        BigDecimal minimumOrderAmount,
        Category category,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {
    public static CouponResponse from(final Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(),
                coupon.getCategory(),
                coupon.getIssuedAt(),
                coupon.getExpiredAt()
        );
    }
}
