package coupon.coupon.dto;

import java.time.LocalDate;

public record MemberCouponRequest(Long couponId, Long memberId, LocalDate issueDate) {
}
