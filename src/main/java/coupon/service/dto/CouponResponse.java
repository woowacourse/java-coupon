package coupon.service.dto;

import coupon.domain.coupon.Coupon;
import java.time.LocalDateTime;

public record CouponResponse(
        Long id,
        String name,
        int discountAmount,
        int minOrderAmount,
        LocalDateTime issueStartedAt,
        LocalDateTime issueEndedAt,
        String category
) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName().getName(),
                coupon.getDiscountAmount().getDiscountAmount(),
                coupon.getMinOrderAmount().getMinOrderAmount(),
                coupon.getIssuePeriod().getIssueStartedAt(),
                coupon.getIssuePeriod().getIssueEndedAt(),
                coupon.getCategory().name()
        );
    }
}
