package coupon.coupon.dto;

import java.time.LocalDateTime;

public record MemberCouponResponse(

        String couponName,
        int couponDiscountAmount,
        int couponMinOrderAmount,
        String couponCategory,
        LocalDateTime couponStartDate,
        LocalDateTime couponEndDate,
        boolean used
) {
}
