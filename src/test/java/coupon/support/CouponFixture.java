package coupon.support;

import coupon.domain.Category;
import coupon.domain.Coupon;

public class CouponFixture {

    public static Coupon createFoodCoupon(String name) {
        return new Coupon(name, 1000L, 10000L, Category.FOOD);
    }
}
