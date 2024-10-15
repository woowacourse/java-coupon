package coupon.domain.coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DiscountAmount {

    private static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(10000);
    private static final BigDecimal DISCOUNT_UNIT = BigDecimal.valueOf(500);

    @Column(name = "discount_amout")
    private BigDecimal value;

    public DiscountAmount(BigDecimal value) {
        validateRange(value);
        validateDiscountUnit(value);
        this.value = value;
    }

    private void validateRange(BigDecimal value) {
        if (value.compareTo(MIN_AMOUNT) < 0 || value.compareTo(MAX_AMOUNT) > 0) {
            throw new CouponException(ExceptionType.COUPON_DISCOUNT_AMOUNT_INVALID);
        }
    }

    private void validateDiscountUnit(BigDecimal value) {
        if (value.remainder(DISCOUNT_UNIT).compareTo(BigDecimal.ZERO) != 0) {
            throw new CouponException(ExceptionType.COUPON_DISCOUNT_UNIT_MISMATCH);
        }
    }

    public BigDecimal getDiscountRate(MinimumOrderAmount minimumOrderAmount) {
        return value.divide(minimumOrderAmount.getValue(), 2, RoundingMode.DOWN)
                .multiply(BigDecimal.valueOf(100));
    }
}
