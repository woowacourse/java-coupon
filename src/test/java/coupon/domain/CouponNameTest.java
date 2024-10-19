package coupon.domain;

import coupon.exception.CouponException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponNameTest {

    @Test
    @DisplayName("쿠폰 이름이 null일 때 예외 발생")
    void createCouponName_NullName_ThrowsException() {
        // given
        String nullName = null;

        // when & then
        Assertions.assertThatThrownBy(() -> new CouponName(nullName))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 이름이 빈 문자열일 때 예외 발생")
    void createCouponName_EmptyName_ThrowsException() {
        // given
        String emptyName = "";

        // when & then
        Assertions.assertThatThrownBy(() -> new CouponName(emptyName))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 이름이 30자를 초과할 때 예외 발생")
    void createCouponName_TooLongName_ThrowsException() {
        // given
        String tooLongName = "1234567890123456789012345678901";

        // when & then
        Assertions.assertThatThrownBy(() -> new CouponName(tooLongName))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰 이름은 최대 30자 이하여야 합니다.");
    }
}
