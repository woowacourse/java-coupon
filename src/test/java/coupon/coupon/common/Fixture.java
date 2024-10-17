package coupon.coupon.common;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.CouponName;
import coupon.coupon.domain.DiscountAmount;
import coupon.coupon.domain.DiscountRate;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.domain.OrderPrice;

public class Fixture {

    public static final CouponName BIG_SALE_COUPON_NAME = new CouponName("초대박 할인");
    public static final DiscountAmount DISCOUNT_AMOUNT_2000 = new DiscountAmount(2000);
    public static final OrderPrice ORDER_PRICE_10000 = new OrderPrice(10000);
    public static final DiscountRate DISCOUNT_RATE_20 = new DiscountRate(
            DISCOUNT_AMOUNT_2000.getAmount(),
            ORDER_PRICE_10000.getValue()
    );

    private Fixture() {
    }

    public static Coupon generateBigSaleFashionCoupon(IssuePeriod issuePeriod) {
        return new Coupon(
                BIG_SALE_COUPON_NAME,
                DISCOUNT_AMOUNT_2000,
                DISCOUNT_RATE_20,
                ORDER_PRICE_10000,
                CouponCategory.FASHION,
                issuePeriod
        );
    }
}
