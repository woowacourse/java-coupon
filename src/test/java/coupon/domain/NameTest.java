package coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import coupon.exception.NameLengthExceedException;
import coupon.exception.NameNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("이름이 비어 있다면 예외가 발생한다.")
    @Test
    void nameEmpty() {
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> new Name(null)).isInstanceOf(NameNotExistException.class),
                () -> assertThatThrownBy(() -> new Name("")).isInstanceOf(NameNotExistException.class),
                () -> assertThatThrownBy(() -> new Name(" ")).isInstanceOf(NameNotExistException.class)
        );
    }

    @DisplayName("이름이 최대 길이를 넘어가면 예외가 발생한다.")
    @Test
    void nameMaxLength() {
        Assertions.assertAll(
                () -> assertThatCode(() -> new Name("@".repeat(30))).doesNotThrowAnyException(),
                () -> assertThatThrownBy(() -> new Name("@".repeat(31))).isInstanceOf(NameLengthExceedException.class)
        );
    }


}
