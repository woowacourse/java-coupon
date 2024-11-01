package coupon.coupon.domain;

import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.CouponException;

@Embeddable
public class MinimumOrderAmount {

    private static final BigDecimal MIN_OF_MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_OF_MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(100000);
    private static final String MINIMUM_ORDER_AMOUNT_RANGE_MESSAGE = String.format(
            "최소 주문 금액은 %s원 이상, %s원 이하이어야 합니다.",
            MIN_OF_MINIMUM_ORDER_AMOUNT.toPlainString(),
            MAX_OF_MINIMUM_ORDER_AMOUNT.toPlainString()
    );

    @Column(nullable = false)
    private BigDecimal minimumOrderAmount;

    protected MinimumOrderAmount() {
    }

    public MinimumOrderAmount(long minimumOrderAmount) {
        this(BigDecimal.valueOf(minimumOrderAmount));
    }

    public MinimumOrderAmount(BigDecimal minimumOrderAmount) {
        validateRangeOfMinimumOrderAmount(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validateRangeOfMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        if (minimumOrderAmount.compareTo(MIN_OF_MINIMUM_ORDER_AMOUNT) < 0 || (minimumOrderAmount.compareTo(MAX_OF_MINIMUM_ORDER_AMOUNT) > 0)) {
            throw new CouponException(MINIMUM_ORDER_AMOUNT_RANGE_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MinimumOrderAmount that = (MinimumOrderAmount) o;
        return Objects.equals(minimumOrderAmount, that.minimumOrderAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minimumOrderAmount);
    }

    public BigDecimal getMinimumOrderAmount() {
        return minimumOrderAmount;
    }
}
