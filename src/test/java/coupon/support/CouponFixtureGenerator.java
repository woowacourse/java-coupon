package coupon.support;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssuancePeriod;
import coupon.domain.coupon.MinOrderAmount;
import coupon.domain.coupon.Name;
import java.time.LocalDateTime;

public class CouponFixtureGenerator {

    public static Coupon generate() {
        return generate(1_000, 10_000);
    }

    public static Coupon generate(int rawDiscountAmount, int rawMinOrderAmount) {
        Name name = new Name("테스트 쿠폰");
        DiscountAmount discountAmount = new DiscountAmount(rawDiscountAmount);
        MinOrderAmount minOrderAmount = new MinOrderAmount(rawMinOrderAmount);
        IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now());
        return new Coupon(1L, name, discountAmount, minOrderAmount, Category.FASHION, issuancePeriod);
    }
}
