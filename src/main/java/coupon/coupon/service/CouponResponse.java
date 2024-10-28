package coupon.coupon.service;

import coupon.coupon.domain.Category;
import coupon.coupon.repository.CouponEntity;
import java.time.LocalDate;

public record CouponResponse(long couponId, String name, Category category,
                             long minimumOrderPrice, long discountPrice,
                             LocalDate issuableStartDate, LocalDate issuableEndDate) {

    public static CouponResponse from(CouponEntity coupon) {
        return new CouponResponse(coupon.getId(), coupon.getName(), coupon.getCategory(),
                coupon.getMinimumOrderPrice(), coupon.getDiscountPrice(),
                coupon.getIssuableStartDate(), coupon.getIssuableEndDate());
    }
}
