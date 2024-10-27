package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("이름으로 null을 입력하면 예외가 발생한다.")
    @Test
    void notNullable() {
        String nameInput = null;

        assertThatThrownBy(() -> new Name(nameInput))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("name은 null이거나, 빈 값일 수 없습니다.");
    }

    @DisplayName("이름의 길이가 30자가 넘는 경우 예외가 발생한다.")
    @Test
    void maxLength30() {
        String nameInput = "a".repeat(31);

        assertThatThrownBy(() -> new Name(nameInput))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("name의 길이는 30자를 초과할 수 없습니다.");
    }
}
