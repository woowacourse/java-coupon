package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @DisplayName("쿠폰 이름이 1자~30자 사이면 생성에 성공한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 30})
    void createName(int length) {
        // given
        String name = Strings.repeat("a", length);

        // when & then
        assertThatCode(() -> new Name(name)).doesNotThrowAnyException();
    }

    @DisplayName("쿠폰 이름이 1자 미만 30자 초과면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 31})
    void createNameFail(int length) {
        // given
        String name = Strings.repeat("a", length);

        // when & then
        assertThatThrownBy(() -> new Name(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 이름은 공백으로만 이루어질 수 없다.")
    @Test
    void createEmptyNameFail() {
        // given
        String name = " ";

        // when & then
        assertThatThrownBy(() -> new Name(name)).isInstanceOf(IllegalArgumentException.class);
    }
}
