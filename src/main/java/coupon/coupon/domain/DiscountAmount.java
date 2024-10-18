package coupon.coupon.domain;

import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.coupon.CouponException;

@Embeddable
public class DiscountAmount {

    private static final BigDecimal UNIT = BigDecimal.valueOf(500);
    private static final BigDecimal MINIMUM_DISCOUNT_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal MAXIMUM_DISCOUNT_AMOUNT = BigDecimal.valueOf(10000);

    @Column(nullable = false)
    private BigDecimal discountAmount;

    protected DiscountAmount() {
    }

    public DiscountAmount(long discountAmount) {
        this(BigDecimal.valueOf(discountAmount));
    }

    public DiscountAmount(BigDecimal discountAmount) {
        validate(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validate(BigDecimal discountAmount) {
        validateUnit(discountAmount);
        validateRangeOfDiscountAmount(discountAmount);
    }

    private void validateUnit(BigDecimal discountAmount) {
        if (!discountAmount.remainder(UNIT).equals(BigDecimal.ZERO)) {
            throw new CouponException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateRangeOfDiscountAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(MINIMUM_DISCOUNT_AMOUNT) < 0 || discountAmount.compareTo(MAXIMUM_DISCOUNT_AMOUNT) > 0) {
            throw new CouponException("할인 금액은 1000원 이상, 10000원 이하이어야 합니다.");
        }
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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
}
