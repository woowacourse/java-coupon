package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountAmountTest {

    @DisplayName("할인 금액의 범위를 넘거가면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {500, 10500})
    void throwException_outRange(int amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 할인 금액입니다.");
    }

    @DisplayName("유효하지 않은 단위의 할인금액인 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1001, 9999})
    void throwException_invalidUnit(int amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 할인 금액 단위입니다.");
    }
}
