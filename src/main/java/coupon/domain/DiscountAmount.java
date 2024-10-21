package coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class DiscountAmount {

    private static final BigDecimal AMOUNT_UNIT = BigDecimal.valueOf(500);
    private static final BigDecimal LOWEST_ALLOWED_AMOUNT = BigDecimal.valueOf(1_000);
    private static final BigDecimal HIGHEST_ALLOWED_AMOUNT = BigDecimal.valueOf(10_000);

    private BigDecimal discountAmount;

    protected DiscountAmount() {
    }

    public DiscountAmount(BigDecimal discountAmount) {
        validate(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validate(BigDecimal discountAmount) {
        if (discountAmount == null) {
            throw new IllegalArgumentException("할인 금액은 필수입니다.");
        }
        if (isMultipleOfAmountUnit(discountAmount)) {
            throw new IllegalArgumentException("할인 금액은 500원 단위여야 합니다.");
        }
        if (isLowerThanLowestAllowedAmount(discountAmount)) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다.");
        }
        if (isUpperThanHighestAllowedAmount(discountAmount)) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하여야 합니다.");
        }
    }

    private boolean isMultipleOfAmountUnit(BigDecimal discountAmount) {
        return discountAmount.remainder(AMOUNT_UNIT).compareTo(BigDecimal.ZERO) != 0;
    }

    private boolean isLowerThanLowestAllowedAmount(BigDecimal discountAmount) {
        return discountAmount.compareTo(LOWEST_ALLOWED_AMOUNT) < 0;
    }

    private boolean isUpperThanHighestAllowedAmount(BigDecimal discountAmount) {
        return discountAmount.compareTo(HIGHEST_ALLOWED_AMOUNT) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountAmount that = (DiscountAmount) o;
        return Objects.equals(discountAmount, that.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
}
