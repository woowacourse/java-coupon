package coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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


}
