package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountMoneyTest {

    @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
    @Test
    void validateMinimumDiscountMoney() {
        assertThatThrownBy(() -> new DiscountMoney(500L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액은 10,000원 이하여야 한다.")
    @Test
    void validateMaximumDiscountMoney() {
        assertThatThrownBy(() -> new DiscountMoney(10500L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
    @Test
    void validateDiscountUnit() {
        assertThatThrownBy(() -> new DiscountMoney(100L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
