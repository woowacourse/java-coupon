package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRateTest {

    private static final BigDecimal MINIMUM_AMOUNT = BigDecimal.valueOf(3.0);
    private static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(20.0);

    @DisplayName("입력한 할인율이 최소 할인율보다 작은 경우, 예외가 발생한다.")
    @Test
    void lessThanMinimumAmount() {
        BigDecimal discountAmount = BigDecimal.valueOf(2000.0);
        BigDecimal minimumOrderAmount = BigDecimal.valueOf(100000.0);

        // 2%
        assertThatThrownBy(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 " + MINIMUM_AMOUNT + "이상, " + MAXIMUM_AMOUNT + "이하의 값이어야 합니다.");
    }

    @DisplayName("입력한 할인율이 최대 할인율보다 큰 경우, 예외가 발생한다.")
    @Test
    void greaterThanMaximumAmount() {
        BigDecimal discountAmount = BigDecimal.valueOf(2100.0);
        BigDecimal minimumOrderAmount = BigDecimal.valueOf(10000.0);

        // 21%
        assertThatThrownBy(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 " + MINIMUM_AMOUNT + "이상, " + MAXIMUM_AMOUNT + "이하의 값이어야 합니다.");
    }
}
