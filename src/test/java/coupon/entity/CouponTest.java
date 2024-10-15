package coupon.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private final String validName = "name";
    private final int validAmount = 5000;
    private final int validMinimumOrder = 50000;

    @DisplayName("쿠폰의 이름은 30자 이하여야한다.")
    @Test
    void validateName() {
        // given
        String invalidName = "thisStringHasLengthWhichIs31abc";

        // when & then
        assertThatThrownBy(() -> new Coupon(invalidName, validAmount, validMinimumOrder))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 할인 금액은 1000원 이상 10000원 이하여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    void validateAmount(int amount) {
        assertThatThrownBy(() -> new Coupon(validName, amount, validMinimumOrder))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰의 최소 주문 금액은 5000원 이상 100000원 이하여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    void validateMinimumOrder(int minimumOrder) {
        assertThatThrownBy(() -> new Coupon(validName, validAmount, minimumOrder))
                .isInstanceOf(CouponException.class);
    }
}
