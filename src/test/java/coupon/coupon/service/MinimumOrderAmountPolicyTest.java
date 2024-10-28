package coupon.coupon.service;

import coupon.coupon.domain.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinimumOrderAmountPolicyTest {

    @DisplayName("최소 주문 금액은 5000원 이상 100000원 이하여야 한다")
    @ParameterizedTest
    @ValueSource(ints = {4999, 100_001})
    void invalidMinimumOrderAmount(int minimumOrderAmount) {
        MinimumOrderAmountPolicy minimumOrderAmountPolicy = new MinimumOrderAmountPolicy();
        assertThatThrownBy(() -> minimumOrderAmountPolicy.validatePolicy(1000, minimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(
                        ExceptionMessage.MINIMUM_ORDER_AMOUNT_EXCEPTION.getMessage(),
                        MinimumOrderAmountPolicy.MIN_MINIMUM_ORDER_AMOUNT,
                        MinimumOrderAmountPolicy.MAX_MINIMUM_ORDER_AMOUNT
                ));
    }
}
