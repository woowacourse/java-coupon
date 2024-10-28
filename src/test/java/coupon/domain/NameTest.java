package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.coupon.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    @DisplayName("이름이 존재하지 않으면 예외 발생")
    void givenNameIsNotExists(String name) {
        assertThatCode(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수 값입니다.");
    }
}
