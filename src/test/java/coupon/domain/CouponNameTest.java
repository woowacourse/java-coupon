package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class
CouponNameTest {

    @Test
    @DisplayName("쿠폰이름을 생성한다.")
    void create_coupon() {
        // given
        final String name = "coupon";

        // when & then
        assertThatCode(() -> new CouponName(name))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿠폰 이름은 최대 30자 이하여야한다.")
    void throw_exception_when_coupon_name_exceed_over_the_30_length() {
        // given
        final String name = "couponcouponcouponcouponcouponc";

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("쿠폰은 반듯이 이름이 존재해야한다.")
    void throw_exception_when_coupon_name_is_empty() {
        // give
        final String name = "";

        // when
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("쿠폰은 반듯이 이름이 존재해야한다.")
    void throw_exception_when_coupon_name_is_null() {
        // give
        final String name = null;

        // when
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("쿠폰은 반듯이 이름이 존재해야한다.")
    void throw_exception_when_coupon_name_is_blank() {
        // give
        final String name = "    ";

        // when
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
