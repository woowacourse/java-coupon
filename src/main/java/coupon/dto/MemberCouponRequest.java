package coupon.dto;

import coupon.domain.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponRequest(
        Long couponId,
        Long memberId,
        LocalDate issueDate
) {
    public MemberCoupon toMemberCoupon() {
        return new MemberCoupon(couponId, memberId, issueDate);
    }
}
