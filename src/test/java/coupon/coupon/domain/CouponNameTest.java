package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponNameTest {

    @Test
    @DisplayName("쿠폰의 이름은 반드시 존재해야 한다.")
    void create() {
        CouponName name = new CouponName("coupon");

        assertThat(name).isEqualTo(new CouponName("coupon"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("쿠폰의 이름이 비어있다면 예외가 발생한다.")
    void invalidCreate(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("쿠폰의 이름은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("이름 길이가 최대 30자 이하여야 한다.")
    void invalidLength() {
        assertThatThrownBy(() -> new CouponName("1234567890123456789012345678901"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("쿠폰 이름은 30자 이하여야 합니다.");
    }
}
