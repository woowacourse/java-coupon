package coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MinimumAmount {

    private static final BigDecimal LOWEST_ALLOWED_AMOUNT = BigDecimal.valueOf(5_000);
    private static final BigDecimal HIGHEST_ALLOWED_AMOUNT = BigDecimal.valueOf(100_000);

    private BigDecimal minimumAmount;

    protected MinimumAmount() {
    }

    public MinimumAmount(BigDecimal minimumAmount) {
        validate(minimumAmount);
        this.minimumAmount = minimumAmount;
    }

    private void validate(BigDecimal minimumAmount) {
        if (minimumAmount == null) {
            throw new IllegalArgumentException("최소 주문 금액은 필수입니다.");
        }
        if (isLowerThanLowestAllowedAmount(minimumAmount)) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다.");
        }
        if (isUpperThanHighestAllowedAmount(minimumAmount)) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하여야 합니다.");
        }
    }

    private boolean isLowerThanLowestAllowedAmount(BigDecimal minimumAmount) {
        return minimumAmount.compareTo(LOWEST_ALLOWED_AMOUNT) < 0;
    }

    private boolean isUpperThanHighestAllowedAmount(BigDecimal minimumAmount) {
        return minimumAmount.compareTo(HIGHEST_ALLOWED_AMOUNT) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MinimumAmount that = (MinimumAmount) o;
        return Objects.equals(minimumAmount, that.minimumAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimumAmount);
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
    }
}
