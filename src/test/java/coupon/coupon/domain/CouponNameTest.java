package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponNameTest {

    @Test
    @DisplayName("실패 : 이름이 빈칸")
    void failEmptyName() {
        assertThatThrownBy(() -> new CouponName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 필수입니다.");
    }

    @Test
    @DisplayName("실패 : 이름이 Null")
    void failNullName() {
        assertThatThrownBy(() -> new CouponName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 필수입니다.");
    }

    @Test
    @DisplayName("실패 : 이름이 31자 이상")
    void failOverMaxName() {
        assertThatThrownBy(() -> new CouponName("a".repeat(31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름 길이는 30자 이하여야 한다.");
    }
}
