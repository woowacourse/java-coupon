package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@Slf4j
class NameTest {

    @DisplayName("이름은 반드시 존재해야 한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void validateExistException(String value) {
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름의 길이는 최대 30자 이하여야 한다.")
    @Nested
    class validateLength {

        @ParameterizedTest
        @ValueSource(ints = {29, 30})
        void validateLengthSuccess(int length) {
            String value = generateValueWithLength(length);
            assertThatCode(() -> new Name(value))
                    .doesNotThrowAnyException();
        }

        @Test
        void validateLengthException() {
            String value = generateValueWithLength(31);
            assertThatThrownBy(() -> new Name(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private String generateValueWithLength(int length) {
            return "a".repeat(length);
        }
    }
}
