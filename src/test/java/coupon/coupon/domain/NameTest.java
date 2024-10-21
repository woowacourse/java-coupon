package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("이름은 반드시 존재해야 한다.")
    @Test
    void validateNameIsEmpty() {
        assertAll(
                () -> assertThatThrownBy(() -> new Name(""))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new Name(null))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("이름의 길이는 최대 30자 이하여야 한다.")
    @Test
    void validateNameLength() {
        assertAll(
                () -> assertThatCode(() -> new Name("tebah"))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new Name("엄청긴스트링엄청긴스트링엄청긴스트링엄청긴스트링엄청긴스트링"))
                        .doesNotThrowAnyException(),
                () -> assertThatThrownBy(() -> new Name("엄청긴스트링엄청긴스트링엄청긴스트링엄청긴스트링엄청긴스트링o"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
