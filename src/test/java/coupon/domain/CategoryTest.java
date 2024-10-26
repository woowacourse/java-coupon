package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CategoryTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("카테고리 이름이 비어 있다면 예외를 발생시킨다.")
    void nameNullTest(String name) {
        assertThatThrownBy(() -> new Category(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카테고리 이름은 필수입니다.");
    }

    @Test
    @DisplayName("올바른 카테고리 이름이라면 생성에 성공한다.")
    void validNameTest() {
        assertThatCode(() -> new Category("카테고리"))
                .doesNotThrowAnyException();
    }
}
