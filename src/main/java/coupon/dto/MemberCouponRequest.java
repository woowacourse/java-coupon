package coupon.dto;

import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponRequest(
        Long couponId,
        Long memberId,
        Boolean isUsed,
        LocalDateTime issuedDateTime
) {
    public MemberCoupon toMemberCoupon() {
        return new MemberCoupon(couponId, memberId, isUsed, issuedDateTime);
    }
}
