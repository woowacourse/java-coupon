package coupon.coupons.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponNameTest {

    @DisplayName("쿠폰 이름이 존재하지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateNameIsNullOrEmpty(String value) {
        assertThatThrownBy(() -> new CouponName(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @DisplayName("쿠폰 이름이 30자 초과일 경우 예외가 발생한다")
    @Test
    void validateNameLength() {
        String tooLongName = "a".repeat(31);

        assertThatThrownBy(() -> new CouponName(tooLongName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 최대 30자 이하여야 합니다.");
    }
}
