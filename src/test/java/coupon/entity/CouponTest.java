package coupon.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("쿠폰의 이름은 30자 이하여야한다.")
    @Test
    void validateName() {
        // given
        String invalidName = "thisStringHasLengthWhichIs31abc";

        // when & then
        assertThatThrownBy(() -> new Coupon(invalidName))
                .isInstanceOf(CouponException.class);
    }
}
