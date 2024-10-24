package coupon.dto;

import java.time.LocalDate;

public record CouponIssueRequest(
        Long couponId,
        Long memberId,
        LocalDate start
) {
}
