package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class MinimumOrderAmount {

    private static final BigDecimal MINIMUM_AMOUNT = BigDecimal.valueOf(5000.0);
    private static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(100000.0);

    private BigDecimal minimumOrderAmount;

    public MinimumOrderAmount(BigDecimal minimumOrderAmount) {
        validateRange(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validateRange(BigDecimal minimumOrderAmount) {
        if (isMinimumOrderAmountLessThan(minimumOrderAmount, MINIMUM_AMOUNT)
                || isMinimumOrderAmountGreaterThan(minimumOrderAmount, MAXIMUM_AMOUNT)) {
            throw new IllegalArgumentException(
                    "최소 주문 금액은 " + MINIMUM_AMOUNT + "이상, " + MINIMUM_AMOUNT + "이하의 값이어야 합니다.");
        }
    }

    private boolean isMinimumOrderAmountLessThan(BigDecimal minimumOrderAmount, BigDecimal value) {
        return minimumOrderAmount.compareTo(value) < 0;
    }

    private boolean isMinimumOrderAmountGreaterThan(BigDecimal discountAmount, BigDecimal value) {
        return discountAmount.compareTo(value) > 0;
    }
}
