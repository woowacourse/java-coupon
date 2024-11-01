package coupon.coupon;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.UserCoupon;
import java.time.LocalDate;

public record UserCouponResponse(
        Long id,
        Long userId,
        boolean isUsed,
        LocalDate issueDate,
        Long couponId,
        String name,
        int discountValue,
        int orderAmount,
        String category,
        LocalDate startDate,
        LocalDate endDate
) {
    public static UserCouponResponse of(UserCoupon userCoupon, Coupon coupon) {
        return new UserCouponResponse(
                userCoupon.getId(),
                userCoupon.getUser().getId(),
                userCoupon.isUsed(),
                userCoupon.getIssueDate(),
                userCoupon.getCouponId(),
                coupon.getName().getName(),
                coupon.getDiscount().getDiscountValue(),
                coupon.getOrder().getAmount(),
                coupon.getCategory().name(),
                coupon.getIssuableDuration().getStartDate(),
                coupon.getIssuableDuration().getEndDate()
        );
    }
}
