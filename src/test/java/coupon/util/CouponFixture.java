package coupon.util;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import java.time.LocalDateTime;

public class CouponFixture {

    public static Coupon createValidFoodCoupon() {
        return new Coupon(
                "할인 쿠폰",
                4000,
                20000,
                CouponCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
    }
}
