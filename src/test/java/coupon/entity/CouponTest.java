package coupon.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private String validName = "name";
    private int validAmount = 5000;

    @DisplayName("쿠폰의 이름은 30자 이하여야한다.")
    @Test
    void validateName() {
        // given
        String invalidName = "thisStringHasLengthWhichIs31abc";

        // when & then
        assertThatThrownBy(() -> new Coupon(invalidName, validAmount))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 할인 금액은 1000원 이상 10000원 이하여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    void validateAmount(int amount) {
        assertThatThrownBy(() -> new Coupon(validName, amount))
                .isInstanceOf(CouponException.class);
    }
}
