package coupon.fixture;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountPrice;
import coupon.domain.coupon.IssueDuration;
import coupon.domain.coupon.SaleOrderPrice;
import java.time.LocalDateTime;

public class CouponFixture {

    public static final Coupon COUPON_FIXTURE = new Coupon(
            1L,
            new CouponName("쿠폰"),
            new DiscountPrice(1000),
            Category.FOOD,
            new SaleOrderPrice(5000),
            new IssueDuration(LocalDateTime.now(), LocalDateTime.now().plusDays(3L))
    );
}
