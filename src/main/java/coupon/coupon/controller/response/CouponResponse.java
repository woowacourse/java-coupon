package coupon.coupon.controller.response;

import java.time.LocalDateTime;

public record CouponResponse(
        long couponId,
        String name,
        int discountAmount,
        int minimumOrderPrice,
        String CouponCategory,
        LocalDateTime issueEndedAt
) {

}
