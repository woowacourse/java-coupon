package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.exception.InvalidDiscountMoneyException;
import coupon.domain.coupon.exception.InvalidDiscountRateException;
import coupon.domain.coupon.exception.InvalidMinOrderMoneyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicDiscountPolicyTest {

    @Test
    @DisplayName("할인 정책에 부합한다.")
    void normal() {
        Assertions.assertDoesNotThrow(() -> new TestDiscountPolicy(1000, 30000));
    }

    @Test
    @DisplayName("할인 금액은 1000원 이상이어야 한다.")
    void tooSmallDiscountMoney() {
        assertThatThrownBy(() -> new TestDiscountPolicy(999, 5000))
                .isInstanceOf(InvalidDiscountMoneyException.class)
                .hasMessage("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 10000원 이하여야 한다.")
    void tooBigDiscountMoney() {
        assertThatThrownBy(() -> new TestDiscountPolicy(10001, 100000))
                .isInstanceOf(InvalidDiscountMoneyException.class)
                .hasMessage("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 500원으로 나누어 떨어져야 한다.")
    void invalidDiscountMoney() {
        assertThatThrownBy(() -> new TestDiscountPolicy(1499, 100000))
                .isInstanceOf(InvalidDiscountMoneyException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @Test
    @DisplayName("할인율은 3% 이상이어야 한다.")
    void tooSmallDiscountRate() {
        assertThatThrownBy(() -> new TestDiscountPolicy(1000, 33334))
                .isInstanceOf(InvalidDiscountRateException.class)
                .hasMessage("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인율은 20% 이하여야 한다.")
    void tooBigDiscountRate() {
        assertThatThrownBy(() -> new TestDiscountPolicy(2000, 9522))
                .isInstanceOf(InvalidDiscountRateException.class)
                .hasMessage("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @Test
    @DisplayName("최소 주문 금액은 5000원 이상이어야 한다.")
    void tooSmallMinOrderMoney() {
        assertThatThrownBy(() -> new TestDiscountPolicy(1000, 4999))
                .isInstanceOf(InvalidMinOrderMoneyException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("최소 주문 금액은 100,000원 이하여야 한다.")
    void tooBigMinOrderMoney() {
        assertThatThrownBy(() -> new TestDiscountPolicy(1000, 100_001))
                .isInstanceOf(InvalidMinOrderMoneyException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }

}
