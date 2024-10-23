package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {999, 10_001, 314, 1592, 6535})
    @DisplayName("유효하지 않은 할인 금액인 경우 예외가 발생한다.")
    void invalid_amount(final int value) {
        assertThatThrownBy(() -> new DiscountAmount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1_000, 10_000, 4_500})
    @DisplayName("정상적인 금액으로 할인 금액을 생성한다.")
    void valid_amount(final int value) {
        final DiscountAmount amount = new DiscountAmount(value);

        assertThat(amount.getValue()).isEqualTo(value);
    }
}
