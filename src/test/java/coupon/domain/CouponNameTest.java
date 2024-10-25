package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 30})
    @DisplayName("쿠폰의 이름을 생성한다.")
    void create(int source) {
        String name = "*".repeat(source);
        assertThatCode(() -> new CouponName(name))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("쿠폰 이름은 반드시 존재해야 한다.")
    void nameIsNotBlank(String source) {
        assertThatThrownBy(() -> new CouponName(source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @Test
    @DisplayName("쿠폰의 이름 길이는 최대 30자 이하여야 한다.")
    void nameMaximumLength() {
        String invalidLength = "*".repeat(31);

        assertThatThrownBy(() -> new CouponName(invalidLength))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름 길이는 최대 30자 이하입니다.");
    }
}
