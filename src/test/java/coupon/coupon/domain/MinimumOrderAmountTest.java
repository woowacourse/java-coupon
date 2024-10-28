package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    @Test
    @DisplayName("최소 주문 금액이 5,000원 미만이면 예외가 발생한다.")
    void should_throw_exception_when_amount_less_than_5000() {
        // given
        int invalidAmount = 4999;

        // when & then
        assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최소 주문 금액이 100,000원을 초과하면 예외가 발생한다.")
    void should_throw_exception_when_amount_more_than_100000() {
        // given
        int invalidAmount = 100001;

        // when & then
        assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 100,000원 이하여야 합니다.");
    }
}
