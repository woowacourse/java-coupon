package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountMoneyPolicyTest {

    @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
    @Test
    void validateMinimumDiscountMoney() {
        assertThatThrownBy(() -> new DiscountMoneyPolicy(500))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액은 10,000원 이하여야 한다.")
    @Test
    void validateMaximumDiscountMoney() {
        assertThatThrownBy(() -> new DiscountMoneyPolicy(10500))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
    @Test
    void validateDiscountUnit() {
        assertThatThrownBy(() -> new DiscountMoneyPolicy(100))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액이 원 가격보다 클 경우 할인 금액은 0이 나온다.")
    @Test
    void validateApply() {
        // given
        int originalPrice = 100;
        DiscountMoneyPolicy policy = new DiscountMoneyPolicy(1000);
        int expected = 0;

        // when
        int actual = policy.apply(originalPrice);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
