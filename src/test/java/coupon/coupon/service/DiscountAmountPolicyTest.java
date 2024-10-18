package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.DiscountPolicyViolationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountPolicyTest {

    private final DiscountAmountPolicy discountAmountPolicy = new DiscountAmountPolicy();

    @ParameterizedTest
    @ValueSource(longs = {500, 999, 10_001, 10_500})
    void 할인_금액이_범위를_벗어나면_예외를_발생한다(long discountAmount) {
        assertThatThrownBy(() -> discountAmountPolicy.validatePolicy(discountAmount, 0))
                .isInstanceOf(DiscountPolicyViolationException.class)
                .hasMessage("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = {1001, 5100})
    void 할인_금액이_500원_단위가_아니면_예외를_발생한다(long discountAmount) {
        assertThatThrownBy(() -> discountAmountPolicy.validatePolicy(discountAmount, 0))
                .isInstanceOf(DiscountPolicyViolationException.class)
                .hasMessage("할인 금액은 500원 단위로 입력되어야 합니다.");
    }
}
