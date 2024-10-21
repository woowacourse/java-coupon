package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("쿠폰을 생성한다.")
    void createCoupon() {
        Coupon coupon = new Coupon(
                "coupon",
                BigDecimal.valueOf(1_000),
                BigDecimal.valueOf(10_000),
                Category.FOOD,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        assertThat("coupon").isEqualTo(coupon.getName());
    }

    @Test
    @DisplayName("쿠폰의 할인율이 3% 미만인 경우 예외가 발생한다.")
    void createCouponWhenDiscountRateLessThan3Percent() {
        // 1000 / 33334 * 100 = 2.99...
        BigDecimal discountAmount = BigDecimal.valueOf(1_000);
        BigDecimal minimumAmount = BigDecimal.valueOf(33_334);

        assertThatThrownBy(() -> new Coupon(
                "coupon",
                discountAmount,
                minimumAmount,
                Category.FOOD,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상이어야 합니다.");
    }

    @Test
    @DisplayName("쿠폰의 할인율이 20% 초과인 경우 예외가 발생한다.")
    void createCouponWhenDiscountRateExceeds20Percent() {
        // 2500 / 11900 * 100 = 21.00...
        BigDecimal discountAmount = BigDecimal.valueOf(2_500);
        BigDecimal minimumAmount = BigDecimal.valueOf(11_900);

        assertThatThrownBy(() -> new Coupon(
                "coupon",
                discountAmount,
                minimumAmount,
                Category.FOOD,
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 20% 이하여야 합니다.");
    }
}
