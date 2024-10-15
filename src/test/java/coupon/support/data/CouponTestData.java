package coupon.support.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.CouponPeriod;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.MinimumOrderAmount;

public class CouponTestData {

    public static CouponBuilder defaultCoupon() {
        return new CouponBuilder()
                .withId(1L)
                .withName(new CouponName("쿠폰"))
                .withDiscountAmount(new DiscountAmount(BigDecimal.valueOf(1000)))
                .withMinimumOrderAmount(new MinimumOrderAmount(BigDecimal.valueOf(5000)))
                .withCategory(CouponCategory.FASHION)
                .withPeriod(new CouponPeriod(
                        LocalDateTime.of(2024, 10, 15, 0, 0),
                        LocalDateTime.of(2024, 10, 16, 0, 0)
                ));
    }

    public static class CouponBuilder {

        private Long id;
        private CouponName name;
        private DiscountAmount discountAmount;
        private MinimumOrderAmount minimumOrderAmount;
        private CouponCategory category;
        private CouponPeriod period;

        public CouponBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CouponBuilder withName(CouponName name) {
            this.name = name;
            return this;
        }

        public CouponBuilder withDiscountAmount(DiscountAmount discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public CouponBuilder withMinimumOrderAmount(MinimumOrderAmount minimumOrderAmount) {
            this.minimumOrderAmount = minimumOrderAmount;
            return this;
        }

        public CouponBuilder withCategory(CouponCategory category) {
            this.category = category;
            return this;
        }

        public CouponBuilder withPeriod(CouponPeriod period) {
            this.period = period;
            return this;
        }

        public Coupon build() {
            return new Coupon(id, name, discountAmount, minimumOrderAmount, category, period);
        }
    }
}
