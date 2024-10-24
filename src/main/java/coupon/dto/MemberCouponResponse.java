package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MemberCouponResponse(
        long id,
        String name,
        BigDecimal discountAmount,
        BigDecimal minimumOrderAmount,
        String category,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
    public static MemberCouponResponse from(
            MemberCoupon memberCoupon,
            Coupon coupon
    ) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(),
                coupon.getCategory().getName(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt()
        );
    }
}
