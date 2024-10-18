package coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    @DisplayName("최소 주문 금액이 정상 생성된다.")
    @Test
    void createMinimumOrderAmountSuccessfully() {
        int validAmount = 5000;

        assertThatNoException()
                .isThrownBy(() -> new MinimumOrderAmount(validAmount));
    }

    @DisplayName("최소 주문 금액이 5,000원 미만이면 예외를 발생시킨다.")
    @Test
    void throwsWhenAmountLessThanMin() {
        int amount = 4999;

        assertThatThrownBy(() -> new MinimumOrderAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상이어야 합니다.");
    }

    @DisplayName("최소 주문 금액이 100,000원 초과하면 예외를 발생시킨다.")
    @Test
    void throwsWhenAmountGreaterThanMax() {
        int amount = 100001;

        assertThatThrownBy(() -> new MinimumOrderAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 100000원 이하여야 합니다.");
    }
}
