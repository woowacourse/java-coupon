package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class DiscountAmount {

    private static final BigDecimal MINIMUM_AMOUNT = BigDecimal.valueOf(1000.0);
    private static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(10000.0);
    private static final BigDecimal DISCOUNT_DIVISOR = BigDecimal.valueOf(500.0);

    private BigDecimal discountAmount;

    public DiscountAmount(BigDecimal discountAmount) {
        validateRange(discountAmount);
        validateDivisibility(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateRange(BigDecimal discountAmount) {
        if (isDiscountAmountLessThan(discountAmount, MINIMUM_AMOUNT)
                || isDiscountAmountGreaterThan(discountAmount, MAXIMUM_AMOUNT)) {
            throw new IllegalArgumentException(
                    "할인 금액은 " + MINIMUM_AMOUNT + "이상, " + MINIMUM_AMOUNT + "이하의 값이어야 합니다.");
        }
    }

    private boolean isDiscountAmountLessThan(BigDecimal discountAmount, BigDecimal value) {
        return discountAmount.compareTo(value) < 0;
    }

    private boolean isDiscountAmountGreaterThan(BigDecimal discountAmount, BigDecimal value) {
        return discountAmount.compareTo(value) > 0;
    }

    private void validateDivisibility(BigDecimal discountAmount) {
        BigDecimal remainder = discountAmount.remainder(DISCOUNT_DIVISOR);
        if (remainder.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("할인 금액은 " + DISCOUNT_DIVISOR + "원 단위로만 설정할 수 있습니다.");
        }
    }
}
