package coupon.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("이름이 빈 값이거나 Null일 경우 예외가 발생한다.")
    @Test
    void throwException_nameIsNullOrEmpty() {
        assertAll(
                () -> assertThatThrownBy(() -> new Name(null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new Name(""))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("이름이 일정 길이가 넘어가면 오류가 발생한다.")
    @Test
    void throwException_exceedNameLength() {
        assertAll(
                () -> assertThatThrownBy(() -> new Name("0".repeat(21)))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
