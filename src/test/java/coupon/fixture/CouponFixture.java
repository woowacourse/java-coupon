package coupon.fixture;

import java.time.LocalDate;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.DiscountAmount;
import coupon.coupon.domain.DiscountRate;
import coupon.coupon.domain.IssuancePeriod;
import coupon.coupon.domain.MinimumOrderAmount;
import coupon.coupon.domain.Name;

public class CouponFixture {

    private static final String NAME = "50% 빅세일 쿠폰";
    private static final Long DISCOUNT_AMOUNT = 1_000L;
    private static final Long MINIMUM_ORDER_AMOUNT = 30_000L;

    public static Coupon createCoupon() {
        LocalDate startDate = LocalDate.from(LocalDate.now());
        LocalDate endDate = LocalDate.from(LocalDate.now().plusDays(7));
        return new Coupon(
                NAME,
                DISCOUNT_AMOUNT,
                MINIMUM_ORDER_AMOUNT,
                startDate,
                endDate);

    }

    public static Coupon createCouponWithId() {
        LocalDate startDate = LocalDate.from(LocalDate.now());
        LocalDate endDate = LocalDate.from(LocalDate.now().plusDays(7));
        return new Coupon(1L,
                new Name(NAME),
                new DiscountAmount(DISCOUNT_AMOUNT),
                DiscountRate.from(DISCOUNT_AMOUNT, MINIMUM_ORDER_AMOUNT),
                new MinimumOrderAmount(MINIMUM_ORDER_AMOUNT),
                new IssuancePeriod(startDate, endDate));

    }
}
