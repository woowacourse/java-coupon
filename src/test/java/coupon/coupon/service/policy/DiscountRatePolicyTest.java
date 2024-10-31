package coupon.coupon.service.policy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.DiscountPolicyViolationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountRatePolicyTest {

    private final DiscountRatePolicy discountRatePolicy = new DiscountRatePolicy(
            new DiscountRatePolicyProperties(3, 20)
    );

    @ParameterizedTest
    @ValueSource(ints = {2, 21})
    void 할인_비율이_범위를_벗어나면_예외를_발생한다(int discountRate) {
        assertThatThrownBy(() -> discountRatePolicy.validatePolicy(discountRate, 100))
                .isInstanceOf(DiscountPolicyViolationException.class)
                .hasMessage("할인율은 3 이상 20 이하여야 합니다.");
    }
}
