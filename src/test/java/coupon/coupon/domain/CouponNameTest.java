package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CouponNameTest {

    @DisplayName("이름이 없으면 예외가 발생한다.")
    @Test
    void cannotCreateIfNoName() {
        assertThatThrownBy(() -> new CouponName(null))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰 이름이 누락되었습니다.");
    }

    @DisplayName("이름이 1자 미만이거나 공백으로만 이루어져 있으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void cannotCreateIfBlank(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰은 30자 이하의 이름을 설정해주세요.");
    }

    @DisplayName("이름이 30자를 초과하면 예외가 발생한다.")
    @Test
    void cannotCreateIfExceedLength() {
        // given
        String name = "가".repeat(31);

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰은 30자 이하의 이름을 설정해주세요.");
    }
}
