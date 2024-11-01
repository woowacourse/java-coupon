package coupon;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @DisplayName("회원의 이름은 최소 1자 이상 10자 이하만 가능하다.")
    @ValueSource(strings = {"a", "1234567890"})
    @ParameterizedTest
    void nameSuccess(String name) {
        assertThatCode(() -> new Member(1L, name))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원의 이름이 비어있거나 10자를 초과하면 에러가 발생한다.")
    @ValueSource(strings = {"", " ", "  ", "\t", "12345678901"})
    @ParameterizedTest
    void nameFail(String name) {
        assertThatCode(() -> new Member(1L, name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
