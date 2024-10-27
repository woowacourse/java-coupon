package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class DiscountRate {

    private static final BigDecimal MINIMUM_AMOUNT = BigDecimal.valueOf(3.0);
    private static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(20.0);

    private BigDecimal discountRate;

    public DiscountRate(BigDecimal discountAmount, BigDecimal minimumOrderAmount) {
        BigDecimal discountRate = discountAmount.multiply(BigDecimal.valueOf(100.0))
                .divide(minimumOrderAmount, RoundingMode.DOWN);
        validateRange(discountRate);
        this.discountRate = discountRate;
    }

    private void validateRange(BigDecimal discountRate) {
        if (isDiscountRateLessThan(discountRate, MINIMUM_AMOUNT)
                || isDiscountRateGreaterThan(discountRate, MAXIMUM_AMOUNT)) {
            throw new IllegalArgumentException(
                    "할인율은 " + MINIMUM_AMOUNT + "이상, " + MAXIMUM_AMOUNT + "이하의 값이어야 합니다.");
        }
    }

    private boolean isDiscountRateLessThan(BigDecimal discountRate, BigDecimal value) {
        return discountRate.compareTo(value) < 0;
    }

    private boolean isDiscountRateGreaterThan(BigDecimal discountRate, BigDecimal value) {
        return discountRate.compareTo(value) > 0;
    }
}
