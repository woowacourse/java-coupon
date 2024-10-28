package coupon.coupon.service;

import coupon.coupon.domain.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountRatePolicyTest {

    @DisplayName("할인률이 3% 미만이면 예외를 발생시킨다")
    @Test
    void discountRateIsNotUnder3() {
        DiscountRatePolicy discountRatePolicy = new DiscountRatePolicy();
        assertThatThrownBy(() -> discountRatePolicy.validatePolicy(2500, 100000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.DISCOUNT_RATE_EXCEPTION.getMessage(),
                        DiscountRatePolicy.MIN_DISCOUNT_RATE, DiscountRatePolicy.MAX_DISCOUNT_RATE));
    }

    @DisplayName("할인률이 20%를 초과하면 예외를 발생시킨다")
    @Test
    void discountRateIsNotOver20() {
        DiscountRatePolicy discountRatePolicy = new DiscountRatePolicy();
        assertThatThrownBy(() -> discountRatePolicy.validatePolicy(1500, 5000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.DISCOUNT_RATE_EXCEPTION.getMessage(),
                        DiscountRatePolicy.MIN_DISCOUNT_RATE, DiscountRatePolicy.MAX_DISCOUNT_RATE));
    }
}
