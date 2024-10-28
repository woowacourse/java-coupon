package coupon.fixture;

import java.time.LocalDateTime;

import coupon.domain.Category;
import coupon.domain.Coupon;

public class CouponFixture {

    public static Coupon create() {
        return new Coupon("테스트 쿠폰", 1000, 10000, LocalDateTime.now(), LocalDateTime.now().plusDays(2), Category.FOOD);
    }
}
