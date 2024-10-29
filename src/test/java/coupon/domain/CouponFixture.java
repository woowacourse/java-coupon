package coupon.domain;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDate;

public class CouponFixture {

    public static final Coupon FOOD_COUPON = new Coupon(
            "food coupon",
            10000,
            1000,
            Category.FOOD,
            LocalDate.now(),
            LocalDate.now().plusDays(7)
    );
}
