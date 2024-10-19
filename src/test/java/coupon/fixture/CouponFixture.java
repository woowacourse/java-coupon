package coupon.fixture;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDateTime;

public class CouponFixture {

    public static final Coupon TEST_COUPON = new Coupon(
            null, "테스트 쿠폰",
            1000, 10000, Category.ELECTRONICS,
            LocalDateTime.now(), LocalDateTime.now().plusDays(7)
    );
}
