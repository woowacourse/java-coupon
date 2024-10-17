package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @ParameterizedTest
    @DisplayName("쿠폰 이름이 빈 값이면 예외가 발생한다.")
    @ValueSource(strings = {"", "  "})
    void should_throw_exception_when_coupon_name_is_blank(String name) {
        // given & when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름이 비어있습니다.");
    }

    @Test
    @DisplayName("쿠폰 이름의 길이가 30글자를 초과하면 예외가 발생한다.")
    void should_throw_exception_when_coupon_name_length_over_30() {
        // given
        String name = "일이삼사오일이삼사오일이삼사오일이삼사오일이삼사오일이삼사오일";

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름의 길이가 30자를 초과합니다. 쿠폰 이름 : " + name);
    }
}
