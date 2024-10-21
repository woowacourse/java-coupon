package coupon.support.data;

import java.time.LocalDate;
import coupon.common.domain.Money;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;

public class CouponTestData {

    public static CouponBuilder defaultCoupon() {
        return new CouponBuilder()
                .withId(null)
                .withName("쿠폰")
                .withDiscountAmount(Money.wons(1_000))
                .withMinOrderAmount(Money.wons(30_000))
                .withCategory(CouponCategory.FASHION)
                .withIssueStartDate(LocalDate.now())
                .withIssueEndDate(LocalDate.now());
    }

    public static class CouponBuilder {

        private Long id;
        private String name;
        private Money discountAmount;
        private Money minOrderAmount;
        private CouponCategory category;
        private LocalDate issueStartDate;
        private LocalDate issueEndDate;

        public CouponBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CouponBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CouponBuilder withDiscountAmount(Money discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public CouponBuilder withMinOrderAmount(Money minOrderAmount) {
            this.minOrderAmount = minOrderAmount;
            return this;
        }

        public CouponBuilder withCategory(CouponCategory category) {
            this.category = category;
            return this;
        }

        public CouponBuilder withIssueStartDate(LocalDate issueStartDate) {
            this.issueStartDate = issueStartDate;
            return this;
        }

        public CouponBuilder withIssueEndDate(LocalDate issueEndDate) {
            this.issueEndDate = issueEndDate;
            return this;
        }

        public Coupon build() {
            return new Coupon(
                    id,
                    name,
                    discountAmount,
                    minOrderAmount,
                    category,
                    issueStartDate,
                    issueEndDate
            );
        }
    }
}
