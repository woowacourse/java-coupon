package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.coupon.domain.CouponName;

public class CouponNameTest {
    private static final int MAX_LENGTH = 30;

    @DisplayName("쿠폰 이름이 공백이면, 예외를 발생한다.")
    @Test
    void testValidateExist_WhenCouponNameIsBlank() {
        // given
        String name = " ";

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 공백일 수 없습니다.");
    }

    @DisplayName("쿠폰 이름의 길이가 최대 길이를 넘으면, 예외를 발생한다.")
    @Test
    void testValidateLength() {
        // given
        String name = "a".repeat(MAX_LENGTH + 1);

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름의 길이는 최대 "+ MAX_LENGTH + "자 이하여야 합니다.");
    }
}
