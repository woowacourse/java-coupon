package coupon.global.domain;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import coupon.coupon.domain.DiscountAmount;
import coupon.coupon.domain.IssuablePeriod;
import coupon.coupon.domain.MinimumOrderAmount;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CouponFixture {

    public static Coupon createCoupon() {
        return new Coupon(new CouponName("coupon"),
                new DiscountAmount(1000),
                new MinimumOrderAmount(10000),
                Category.ELECTRONICS,
                new IssuablePeriod(LocalDate.now(), LocalDate.now().plusDays(7))
        );
    }
}
