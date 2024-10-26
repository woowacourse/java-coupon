package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름 생성 실패: 이름이 Null 인 경우")
    void createName(String invalidName) {
        assertThatThrownBy(() -> new Name(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 null이 될 수 없습니다.");
    }

    @Test
    @DisplayName("이름 생성 실패: 이름이 30자를 초과하는 경우")
    void createName() {
        String invalidName = "a".repeat(31);
        assertThatThrownBy(() -> new Name(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 최대 30자 이하여야 합니다.");
    }
}
