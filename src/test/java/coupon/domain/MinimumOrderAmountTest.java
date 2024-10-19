package coupon.domain;

import coupon.exception.CouponException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    @Test
    @DisplayName("최소 주문 금액이 5,000원 미만일 때 예외 발생")
    void validateMinimumOrderAmount_TooLow_ThrowsException() {
        // given
        int invalidAmount = 4_000;

        // when & then
        Assertions.assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("최소 주문 금액이 100,000원을 초과할 때 예외 발생")
    void validateMinimumOrderAmount_TooHigh_ThrowsException() {
        // given
        int invalidAmount = 150_000;

        // when & then
        Assertions.assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
    }
}
