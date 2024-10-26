package coupon.coupon.dto;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponCreateRequest(
        String name,
        Long issuerId,
        BigDecimal discountAmount,
        BigDecimal minimumOrderAmount,
        String category,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt
) {

    public Coupon toCouponEntity(final Member issuer) {
        return new Coupon(
                name,
                issuer,
                discountAmount,
                minimumOrderAmount,
                Category.valueOf(category),
                issuedAt,
                expiredAt
        );
    }
}
