package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinOrderAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {4_999, 100_001})
    @DisplayName("유효하지 않은 최소 주문 금액인 경우 예외가 발생한다.")
    void invalid_value(final int value) {
        assertThatThrownBy(() -> new MinOrderAmount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {5_000, 10_200, 100_000})
    @DisplayName("정상적인 최소 금액인 경우 객체를 생성한다.")
    void valid_amount(final int amount) {
        //given && when
        final MinOrderAmount minOrderAmount = new MinOrderAmount(amount);

        //then
        assertThat(minOrderAmount.getAmount()).isEqualTo(amount);
    }
}
