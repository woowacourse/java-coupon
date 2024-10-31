package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumAmountTest {

    @DisplayName("최소 주문 금액이 최소 금액보다 적거나 최대 금액보다 많으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 21, 22, 23, 100})
    void throwsExceptionWhenNotInRange(int amount) {
        String expectedMessage = "최소 주문 금액은 %d 이상 %d 이하여야 합니다. 입력값 : ".formatted(5_000, 100_000) + amount;

        assertThatThrownBy(() -> new MinimumAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }
}
