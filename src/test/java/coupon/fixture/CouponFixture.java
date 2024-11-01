package coupon.fixture;

import java.time.LocalDate;
import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;

public class CouponFixture {

    public static Coupon create(LocalDate startAt, LocalDate endAt) {
        return new Coupon("coupon", 1000, 10000, Category.FASHION, startAt, endAt);
    }
}
