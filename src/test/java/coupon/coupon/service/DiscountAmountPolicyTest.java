package coupon.coupon.service;

import coupon.coupon.domain.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountAmountPolicyTest {

    @DisplayName("할인 금액은 1000원 이상 10000원 이하, 단위는 500원이어야 한다")
    @ParameterizedTest
    @ValueSource(ints = {999, 10001, 1501})
    void invalidDiscountAmount(int discountAmount) {
        DiscountAmountPolicy discountAmountPolicy = new DiscountAmountPolicy();
        assertThatThrownBy(() -> discountAmountPolicy.validatePolicy(discountAmount, 5000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.DISCOUNT_AMOUNT_EXCEPTION.getMessage(),
                        DiscountAmountPolicy.MIN_DISCOUNT_AMOUNT, DiscountAmountPolicy.MAX_DISCOUNT_AMOUNT,
                        DiscountAmountPolicy.DISCOUNT_AMOUNT_UNIT));
    }
}
