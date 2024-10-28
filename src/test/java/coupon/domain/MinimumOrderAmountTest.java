package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.exception.InvalidMinimumOrderAmount;

class MinimumOrderAmountTest {

    @Test
    @DisplayName("최소 주문 금액이 음수일 경우 예외가 발생한다.")
    void throw_exception_minus_value() {
        assertThatCode(() -> new MinimumOrderAmount(-1))
                .isExactlyInstanceOf(InvalidMinimumOrderAmount.class);
    }
}
