package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponNameTest {

    @DisplayName("이름에 공백을 제외하고 빈 값이 들어오면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void nameIsNotBlank(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.NAME_LENGTH_EXCEPTION.getMessage(), CouponName.MAX_NAME_LENGTH));
    }

    @DisplayName("이름이 30글자를 초과하면 예외를 발생시킨다")
    @Test
    void nameLengthIsNotOver30() {
        assertThatThrownBy(() -> new CouponName("f".repeat(31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.NAME_LENGTH_EXCEPTION.getMessage(), CouponName.MAX_NAME_LENGTH));
    }
}
