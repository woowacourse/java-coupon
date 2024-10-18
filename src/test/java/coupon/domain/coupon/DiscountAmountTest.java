package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    private static final int MIN_VALUE = 1_000;
    private static final int MAX_VALUE = 10_000;
    private static final int UNIT_OF_VALUE = 500;

    @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
    @Nested
    class validateMin {

        @ParameterizedTest
        @ValueSource(ints = {MIN_VALUE, MIN_VALUE + UNIT_OF_VALUE})
        void validateMinSuccess(int value) {
            assertThatCode(() -> new DiscountAmount(value))
                    .doesNotThrowAnyException();
        }

        @Test
        void validateMinException() {
            assertThatThrownBy(() -> new DiscountAmount(MIN_VALUE - UNIT_OF_VALUE))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("할인 금액은 10,000원 이하여야 한다.")
    @Nested
    class validateMax {

        @ParameterizedTest
        @ValueSource(ints = {MAX_VALUE, MAX_VALUE - UNIT_OF_VALUE})
        void validateMaxSuccess(int value) {
            assertThatCode(() -> new DiscountAmount(value))
                    .doesNotThrowAnyException();
        }

        @Test
        void validateMaxException() {
            assertThatThrownBy(() -> new DiscountAmount(MAX_VALUE + UNIT_OF_VALUE))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
    @Nested
    class validateUnit {

        private static final int BASE_VALUE = MIN_VALUE + UNIT_OF_VALUE;

        @Test
        void validateUnitSuccess() {
            assertThatCode(() -> new DiscountAmount(BASE_VALUE))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {BASE_VALUE + 1, BASE_VALUE - 1})
        void validateUnitException(int value) {
            assertThatThrownBy(() -> new DiscountAmount(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
