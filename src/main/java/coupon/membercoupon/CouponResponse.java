package coupon.membercoupon;

import java.time.LocalDate;

public record CouponResponse(
        String couponName,
        int discountAmount,
        int minOrderAmount,
        String category,
        boolean isUsed,
        LocalDate issuedAt,
        LocalDate expiredAt
) {
}
