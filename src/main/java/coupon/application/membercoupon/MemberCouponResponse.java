package coupon.application.membercoupon;

import java.time.LocalDate;

public record MemberCouponResponse(
        Long id,
        Long memberId,
        IssuedCouponResponse coupon,
        Boolean isUsed,
        LocalDate issuedAt,
        LocalDate expiresAt
) {
}
