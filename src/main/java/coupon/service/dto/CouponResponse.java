package coupon.service.dto;

import coupon.domain.Coupon;
import java.time.LocalDateTime;

public record CouponResponse(
        Long id,
        String name,
        Long discountAmount,
        Long minOrderAmount,
        LocalDateTime issueStartedAt,
        LocalDateTime issueEndedAt,
        String category
) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(),
                coupon.getIssueStartDate(),
                coupon.getIssueEndDate(),
                coupon.getCategory().name()
        );
    }
}
