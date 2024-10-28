package coupon.coupons.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.coupons.domain.PricingCondition;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PricingConditionTest {

    @DisplayName("할인 금액이 존재하지 않는 경우 예외가 발생한다")
    @Test
    void validateDiscountIsNull() {
        assertThatThrownBy(() -> new PricingCondition(null, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 반드시 존재해야 합니다.");
    }

    @DisplayName("최소 주문 금액이 존재하지 않는 경우 예외가 발생한다")
    @Test
    void validateMinOrderAmountIsNull() {
        assertThatThrownBy(() -> new PricingCondition(2000, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 반드시 존재해야 합니다.");
    }

    @DisplayName("할인 금액이 1,000원 이상 10,000원 이하가 아닌 경우 예외가 발생한다")
    @Test
    void validateDiscountRange() {
        int discountAmount = 500;

        assertThatThrownBy(() -> new PricingCondition(discountAmount, 5000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 500원 단위가 아닐 때 예외가 발생한다")
    @Test
    void validateDiscountUnit() {
        int discountAmount = 1250;

        assertThatThrownBy(() -> new PricingCondition(discountAmount, 5000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @DisplayName("할인율이 3% 미만 또는 20% 초과시 예외가 발생한다")
    @Test
    void validateDiscountRateRange() {
        int discountAmount = 8000;
        int minOrderAmount = 10000;

        assertThatThrownBy(() -> new PricingCondition(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @DisplayName("최소 주문 금액이 5,000원 이상 100,000원 이하가 아닌 경우 예외가 발생한다")
    @Test
    void validateMinOrderAmount() {
        int invalidMinOrderAmount = 100500;

        assertThatThrownBy(() -> new PricingCondition(10000, invalidMinOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }
}
