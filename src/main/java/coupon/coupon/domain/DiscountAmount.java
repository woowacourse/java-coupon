package coupon.coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.CouponException;

@Embeddable
public class DiscountAmount {

    private static final BigDecimal UNIT = BigDecimal.valueOf(500);
    private static final BigDecimal MINIMUM_DISCOUNT_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal MAXIMUM_DISCOUNT_AMOUNT = BigDecimal.valueOf(10000);
    private static final String DISCOUNT_AMOUNT_UNIT_MESSAGE = String.format("할인 금액은 %s원 단위로 설정할 수 있습니다.", UNIT);
    private static final String DISCOUNT_AMOUNT_RANGE_MESSAGE = String.format(
            "할인 금액은 %s원 이상, %s원 이하이어야 합니다.",
            MINIMUM_DISCOUNT_AMOUNT.toPlainString(),
            MAXIMUM_DISCOUNT_AMOUNT.toPlainString()
    );

    @Column(nullable = false)
    private BigDecimal discountAmount;

    protected DiscountAmount() {
    }

    public DiscountAmount(long discountAmount) {
        this(BigDecimal.valueOf(discountAmount));
    }

    private DiscountAmount(BigDecimal discountAmount) {
        validate(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validate(BigDecimal discountAmount) {
        validateUnit(discountAmount);
        validateRangeOfDiscountAmount(discountAmount);
    }

    private void validateUnit(BigDecimal discountAmount) {
        if (!discountAmount.remainder(UNIT).equals(BigDecimal.ZERO)) {
            throw new CouponException(DISCOUNT_AMOUNT_UNIT_MESSAGE);
        }
    }

    private void validateRangeOfDiscountAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(MINIMUM_DISCOUNT_AMOUNT) < 0 || discountAmount.compareTo(MAXIMUM_DISCOUNT_AMOUNT) > 0) {
            throw new CouponException(DISCOUNT_AMOUNT_RANGE_MESSAGE);
        }
    }

    public BigDecimal getDiscountRate(MinimumOrderAmount minimumOrderAmount) {
        return discountAmount.multiply(BigDecimal.valueOf(100))
                .divide(minimumOrderAmount.getMinimumOrderAmount(), 0, RoundingMode.DOWN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DiscountAmount that = (DiscountAmount) o;
        return Objects.equals(discountAmount, that.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }
}
