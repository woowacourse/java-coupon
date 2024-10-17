package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponDiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    void 할인_금액이_범위_밖이면_예외를_발생한다(int discountAmount) {
        int minOrderAmount = discountAmount / 10;
        assertThatCode(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 1001})
    void 할인_금액의_단위가_올바르지_않으면_예외를_발생한다(int discountAmount) {
        int minOrderAmount = discountAmount / 10;
        assertThatCode(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 21})
    void 할인율이_범위_밖이면_예외를_발생한다(int discountRate) {
        int minOrderAmount = 10000;
        int discountAmount = minOrderAmount * discountRate;
        assertThatCode(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
