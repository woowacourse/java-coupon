package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @DisplayName("쿠폰 이름이 비어 있으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void invalidCouponName(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 이름이 존재하면 예외가 발생하지 않는다.")
    @Test
    void validCouponName() {
        assertThatCode(() -> new CouponName("kaki"))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰 이름이 정해진 길이 제한을 초과하면 예외가 발생한다.")
    @Test
    void invalidCouponNameLength() {
        String name = "a".repeat(CouponName.COUPON_NAME_LENGTH + 1);

        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 이름이 정해진 길이 제한을 초과하지 않으면 예외가 발생하지 않는다.")
    @Test
    void validCouponNameLength() {
        String name = "a".repeat(CouponName.COUPON_NAME_LENGTH);

        assertThatCode(() -> new CouponName(name))
                .doesNotThrowAnyException();
    }
}
