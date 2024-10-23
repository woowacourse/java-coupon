package coupon.util;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponFixture {

    public static Coupon createCoupon() {
        return new Coupon(
                "배민 10% 할인 쿠폰",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusDays(2)
        );
    }
}
