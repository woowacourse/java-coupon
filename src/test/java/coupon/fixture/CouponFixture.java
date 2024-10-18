package coupon.fixture;

import java.time.LocalDate;

import coupon.coupon.domain.Coupon;

public class CouponFixture {

    private static final String NAME = "50% 빅세일 쿠폰";
    private static final Long DISCOUNT_AMOUNT = 10000L;
    private static final Double DISCOUNT_RATE = 10.0;
    private static final Integer MINIMUM_ORDER_AMOUNT = 5000;

    public static Coupon createCoupon() {
        LocalDate startDate = LocalDate.from(LocalDate.now());
        LocalDate endDate = LocalDate.from(LocalDate.now().plusDays(7));
        return new Coupon(
                NAME,
                DISCOUNT_AMOUNT,
                DISCOUNT_RATE,
                MINIMUM_ORDER_AMOUNT,
                startDate,
                endDate);

    }
}
