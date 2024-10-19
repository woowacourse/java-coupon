package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.exception.InvalidCouponDiscountRate;

class DiscountRateTest {

    @Test
    @DisplayName("할인율이 최소값 미만일 경우 예외가 발생한다.")
    void throw_exception_under_min_value() {
        assertThatCode(() -> new DiscountRate(2))
                .isExactlyInstanceOf(InvalidCouponDiscountRate.class);
    }

    @Test
    @DisplayName("할인율이 최대값 초과일 경우 예외가 발생한다.")
    void throw_exception_over_min_value() {
        assertThatCode(() -> new DiscountRate(21))
                .isExactlyInstanceOf(InvalidCouponDiscountRate.class);
    }
}
