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
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinOrderAmount(),
                coupon.getIssueStartedAt(),
                coupon.getIssueEndedAt(),
                coupon.getCategory().name()
        );
    }
}
