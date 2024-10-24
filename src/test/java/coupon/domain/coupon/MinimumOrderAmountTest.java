package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {5000, 100000})
    @DisplayName("최소 주문 금액이 잘 생성된다.")
    void minimumOrderAmount(int amount) {
        assertDoesNotThrow(() -> new MinimumOrderAmount(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    @DisplayName("최소 주문 금액이 5000원 미만이거나 100000원 초과일 경우 예외가 발생한다.")
    void validateRange(int amount) {
        assertThatThrownBy(() -> new MinimumOrderAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
    }
}
