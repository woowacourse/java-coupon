package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @Test
    @DisplayName("이름이 잘 생성된다.")
    void name() {
        assertDoesNotThrow(() -> new Name("123456789012345678901234567890"));
    }

    @Test
    @DisplayName("이름의 길이가 30자 초과일 경우 예외가 발생한다.")
    void validateLength() {
        assertThatThrownBy(() -> new Name("1234567890123456789012345678901"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름의 길이는 30자를 넘어갈 수 없습니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름이 비어있을 경우 예외가 발생한다.")
    void validateEmpty(String name) {
        assertThatThrownBy(() -> new Name(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이름이 공백일 경우 예외가 발생한다.")
    void validateBlank() {
        assertThatThrownBy(() -> new Name(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름이 존재하지 않습니다.");
    }
}
