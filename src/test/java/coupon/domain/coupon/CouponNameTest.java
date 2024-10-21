package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @DisplayName("이름은 비어있으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "     "})
    void throwsExceptionInvalidName(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("이름은 31자 이상이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcdefghijklmnopqrstuvwxyzABCDE", "abcdefghijklmnopqrstuvwxyzABCDEF"})
    void throwsExceptionInvalidNameLength(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름의 길이는 최대 30자 이하여야 한다. 입력 값 : " + name.length());
    }
}
