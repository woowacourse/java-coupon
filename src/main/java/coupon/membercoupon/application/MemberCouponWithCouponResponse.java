package coupon.membercoupon.application;

import java.time.LocalDateTime;
import coupon.coupon.application.CouponResponse;

public record MemberCouponWithCouponResponse(
        Long id,
        Long couponId,
        Long memberId,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt,
        CouponResponse coupon
) {
}
