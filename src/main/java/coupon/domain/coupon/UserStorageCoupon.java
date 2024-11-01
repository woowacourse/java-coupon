package coupon.domain.coupon;

import coupon.entity.Coupon;

public record UserStorageCoupon(String name, int discountPrice, int minimumPrice, String categoryName) {

    public static UserStorageCoupon of(Coupon coupon) {
        return new UserStorageCoupon(
                coupon.getName(),
                coupon.getDiscountPrice(),
                coupon.getMinimumOrderPrice(),
                coupon.getCategory().getName()
        );
    }
}
