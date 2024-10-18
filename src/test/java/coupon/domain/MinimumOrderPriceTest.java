package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinimumOrderPriceTest {

    @DisplayName("유효하지 않은 최소 주문 금액의 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    void throwException_invalidAmount(int amount) {
        assertThatThrownBy(() -> new MinimumOrderPrice(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 최소 주문 금액입니다.");
    }
}
