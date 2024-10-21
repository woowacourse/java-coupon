package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponNameTest {

    @Test
    @DisplayName("쿠폰 이름을 생성한다.")
    void createCouponName() {
        String name = "coupon";

        CouponName couponName = new CouponName(name);

        assertThat(couponName).isEqualTo(new CouponName("coupon"));
    }

    @Test
    @DisplayName("쿠폰 이름이 null인 경우 예외가 발생한다.")
    void createCouponNameWhenNull() {
        String nullName = null;

        assertThatThrownBy(() -> new CouponName(nullName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 필수입니다.");
    }

    @ParameterizedTest
    @DisplayName("쿠폰 이름이 없는 경우 예외가 발생한다.")
    @CsvSource(value = {"''", "' '", "'    '"})
    void createCouponNameWhenNoName(String blankName) {
        assertThatThrownBy(() -> new CouponName(blankName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 필수입니다.");
    }

    @Test
    @DisplayName("쿠폰 이름이 30자를 초과하면 예외가 발생한다.")
    void createCouponNameWhenNameLengthExceeds30() {
        String tooLongName = "0123456789012345678901234567890";

        assertThatThrownBy(() -> new CouponName(tooLongName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 30자 이하여야 합니다.");
    }
}
