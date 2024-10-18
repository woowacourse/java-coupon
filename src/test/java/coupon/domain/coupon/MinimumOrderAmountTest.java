package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderAmountTest {

    private static final int MIN_VALUE = 5_000;
    private static final int MAX_VALUE = 100_000;

    @DisplayName("최소 주문 금액은 5,000원 이상이어야 한다.")
    @Nested
    class validateMin {

        @ParameterizedTest
        @ValueSource(ints = {MIN_VALUE, MIN_VALUE + 1})
        void validateMinSuccess(int value) {
            assertThatCode(() -> new MinimumOrderAmount(value))
                    .doesNotThrowAnyException();
        }

        @Test
        void validateMinException() {
            assertThatThrownBy(() -> new MinimumOrderAmount(MIN_VALUE - 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("최소 주문 금액은 100,000원 이하여야 한다.")
    @Nested
    class validateMax {

        @ParameterizedTest
        @ValueSource(ints = {MAX_VALUE, MAX_VALUE - 1})
        void validateMaxSuccess(int value) {
            assertThatCode(() -> new MinimumOrderAmount(value))
                    .doesNotThrowAnyException();
        }

        @Test
        void validateMaxException() {
            assertThatThrownBy(() -> new MinimumOrderAmount(MAX_VALUE + 1))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
