package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @Test
    @DisplayName("쿠폰 이름이 정상 생성된다.")
    void createCouponName() {
        // given
        String name = "쿠폰 이름";

        // when & then
        assertThatCode(() -> new CouponName(name))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿠폰 이름은 앞뒤 공백이 제거 후 생성된다.")
    void createCouponNameWithStrip() {
        // given
        String name = " 쿠폰 이름 ";

        // when
        CouponName couponName = new CouponName(name);

        // then
        assertThat(couponName.getValue()).isEqualTo("쿠폰 이름");
    }

    @ParameterizedTest
    @DisplayName("쿠폰 이름은 공백일 수 없다.")
    @ValueSource(strings = {"", " "})
    void validateCouponNameNotBlank(String name) {
        // when & then
        assertThatCode(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 30자 이하의 비어 있지 않은 문자열이어야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 이름은 30자를 초과할 수 없다.")
    void validateCouponNameLength() {
        // given
        String name = "a".repeat(31);

        // when & then
        assertThatCode(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 30자 이하의 비어 있지 않은 문자열이어야 합니다.");
    }
}
