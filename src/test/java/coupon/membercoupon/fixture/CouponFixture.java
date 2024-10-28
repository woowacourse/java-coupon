package coupon.membercoupon.fixture;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;

import java.time.LocalDateTime;

public final class CouponFixture {

    public static Coupon getCoupon(){
        return new Coupon("가을 맞이 쿠폰", 1000, 10000,
                Category.FASHION, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
    }
}
