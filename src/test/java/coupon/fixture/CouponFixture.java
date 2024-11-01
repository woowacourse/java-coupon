package coupon.fixture;

import java.time.LocalDateTime;

import coupon.domain.Category;
import coupon.domain.Coupon;

public class CouponFixture {

    public static Coupon coupon_1 = new Coupon(
            "coupon1",
            1000L,
            10000L,
            Category.FOOD,
            LocalDateTime.now(),
            LocalDateTime.now());

    public static Coupon coupon_2 = new Coupon(
            "coupon2",
            1000L,
            10000L,
            Category.FOOD,
            LocalDateTime.now(),
            LocalDateTime.now());
}
