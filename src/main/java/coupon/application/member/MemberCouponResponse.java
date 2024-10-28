package coupon.application.member;

import java.time.LocalDateTime;

public record MemberCouponResponse(
        Long id,
        Long memberId,
        IssuedCouponResponse coupon,
        Boolean isUsed,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
}
