package coupon.fixture;

import java.time.LocalDate;

import coupon.coupon.domain.Coupon;

public class CouponFixture {

    private static final String NAME = "50% 빅세일 쿠폰";
    private static final Long DISCOUNT_AMOUNT = 1_000L;
    private static final Integer MINIMUM_ORDER_AMOUNT = 30_000;

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
}
