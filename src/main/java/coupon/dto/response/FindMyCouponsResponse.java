package coupon.dto.response;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDate;

public record FindMyCouponsResponse(
        long couponId,
        String couponName,
        long discountMoney,
        long minimumOrderMoney,
        LocalDate sinceDate,
        LocalDate untilDate,
        Category category,

        boolean isUsed,
        LocalDate issueDate,
        LocalDate expireDate
) {

    public FindMyCouponsResponse(Coupon coupon, MemberCoupon memberCoupon) {
        this(coupon.getId(),
                coupon.getName(),
                coupon.getDiscountMoney(),
                coupon.getMinimumOrderMoney(),
                coupon.getSinceDate(),
                coupon.getUntilDate(),
                coupon.getCategory(),
                memberCoupon.isUsed(),
                memberCoupon.getIssueDate(),
                memberCoupon.checkExpireDate());
    }
}
