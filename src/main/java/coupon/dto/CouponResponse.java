package coupon.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;

public record CouponResponse(long id,
                             String couponName,
                             int discountPrice,
                             int minOrderPrice,
                             Category category,
                             DurationResponse duration

) {
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getCouponName().getName(),
                coupon.getDiscountPrice().getPrice(),
                coupon.getMinOrderPrice().getPrice(), coupon.getCategory(),
                new DurationResponse(
                        coupon.getDuration().getStartDate(),
                        coupon.getDuration().getEndDate()
                )
        );
    }
}
