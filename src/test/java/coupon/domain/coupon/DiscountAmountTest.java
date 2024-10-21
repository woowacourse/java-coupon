package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @DisplayName("할인 금액이 최소 할인 금액보다 적거나 최대 할인 금액보다 많으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 999, 10_001, 10_100})
    void throwsExceptionNotInRange(int amount) {
        String expectedMessage = "할인 금액은 %d 이상 %d 이하여야 합니다. 입력 값: ".formatted(1_000, 10_000) + amount;

        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @DisplayName("할인 금액이 단위 할인 금액으로 나누어 떨어지지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1001, 1010, 1200, 1900, 9999})
    void throwsExceptionRemainedModularByUnit(int amount) {
        String expectedMessage = "할인 금액은 %d원 단위로 설정해야 합니다. 입력 값: ".formatted(500) + amount;

        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }
}
