package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    private static final BigDecimal MINIMUM_AMOUNT = BigDecimal.valueOf(5000.0);
    private static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(100000.0);

    @DisplayName("입력한 최소 주문 금액이 최소 금액보다 작은 경우, 예외가 발생한다.")
    @Test
    void lessThanMinimumAmount() {
        BigDecimal minimumOrderAmount = MINIMUM_AMOUNT.subtract(BigDecimal.ONE);

        assertThatThrownBy(() -> new MinimumOrderAmount(minimumOrderAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 " + MINIMUM_AMOUNT + "이상, " + MINIMUM_AMOUNT + "이하의 값이어야 합니다.");
    }

    @DisplayName("입력한 최소 주문 금액이 최대 금액보다 큰 경우, 예외가 발생한다.")
    @Test
    void greaterThanMaximumAmount() {
        BigDecimal minimumOrderAmount = MAXIMUM_AMOUNT.add(BigDecimal.ONE);

        assertThatThrownBy(() -> new MinimumOrderAmount(minimumOrderAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 " + MINIMUM_AMOUNT + "이상, " + MINIMUM_AMOUNT + "이하의 값이어야 합니다.");
    }
}
