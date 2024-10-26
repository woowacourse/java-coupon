package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    private static final BigDecimal MINIMUM_AMOUNT = BigDecimal.valueOf(1000.0);
    private static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(10000.0);
    private static final BigDecimal DISCOUNT_DIVISOR = BigDecimal.valueOf(500.0);

    @DisplayName("입력한 할인 금액이 최소 할인 금액보다 작은 경우, 예외가 발생한다.")
    @Test
    void lessThanMinimumAmount() {
        BigDecimal discountAmount = MINIMUM_AMOUNT.subtract(BigDecimal.ONE);

        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + MINIMUM_AMOUNT + "이상, " + MAXIMUM_AMOUNT + "이하의 값이어야 합니다.");
    }

    @DisplayName("입력한 할인 금액이 최대 할인 금액보다 큰 경우, 예외가 발생한다.")
    @Test
    void greaterThanMaximumAmount() {
        BigDecimal discountAmount = MAXIMUM_AMOUNT.add(BigDecimal.ONE);

        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + MINIMUM_AMOUNT + "이상, " + MAXIMUM_AMOUNT + "이하의 값이어야 합니다.");
    }

    @DisplayName("입력한 할인 금액이 할인 금액 입력 단위로 나누어 떨어지지 않는 경우, 예외가 발생한다.")
    @Test
    void notDivisibleAmount() {
        BigDecimal discountAmount = MINIMUM_AMOUNT.add(BigDecimal.ONE);
        System.out.println(discountAmount);

        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + DISCOUNT_DIVISOR + "원 단위로만 설정할 수 있습니다.");
    }
}
