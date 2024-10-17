package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponMinOrderAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {4999, 100_001})
    void 쿠폰의_최소_주문_금액이_범위를_벗어나면_예외를_발생한다(int minOrderAmount) {
        assertThatCode(() -> new CouponMinOrderAmount(minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
