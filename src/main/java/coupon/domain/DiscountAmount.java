package coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class DiscountAmount {

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
        if (discountAmount.remainder(BigDecimal.valueOf(500)).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위여야 합니다.");
        }
        if (discountAmount.compareTo(BigDecimal.valueOf(1_000)) < 0) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다.");
        }
        if (discountAmount.compareTo(BigDecimal.valueOf(10_000)) > 0) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하여야 합니다.");
        }
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
