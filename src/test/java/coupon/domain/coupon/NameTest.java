package coupon.domain.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;

import coupon.exception.NameLengthExceedException;
import coupon.exception.EmptyNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @DisplayName("이름이 비어 있다면 예외가 발생한다.")
    @Test
    void nameEmpty() {
        Assertions.assertAll(
                () -> assertThatThrownBy(() -> new Name(null)).isInstanceOf(EmptyNameException.class),
                () -> assertThatThrownBy(() -> new Name("")).isInstanceOf(EmptyNameException.class),
                () -> assertThatThrownBy(() -> new Name(" ")).isInstanceOf(EmptyNameException.class)
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
