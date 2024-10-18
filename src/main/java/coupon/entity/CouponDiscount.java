package coupon.entity;

import coupon.exception.CouponDiscountAmountException;
import coupon.exception.CouponDiscountRateException;
import coupon.exception.CouponMinimumOrderAmountException;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class CouponDiscount {

    private BigDecimal discountAmount;
    private BigDecimal minimumOrderAmount;

    public CouponDiscount(BigDecimal discountAmount, BigDecimal minimumOrderAmount) {
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        validate();
    }

    private void validate() {
        validateDiscountAmount();
        validateMinimumOrderAmount();
        validateDiscountRate();
    }

    private void validateDiscountAmount() {
        if (discountAmount.compareTo(BigDecimal.valueOf(1000)) < 0 ||
                discountAmount.compareTo(BigDecimal.valueOf(10000)) > 0 ||
                discountAmount.remainder(BigDecimal.valueOf(500)).compareTo(BigDecimal.ZERO) != 0) {
            throw new CouponDiscountAmountException();
        }
    }

    private void validateMinimumOrderAmount() {
        if (minimumOrderAmount.compareTo(BigDecimal.valueOf(5000)) < 0 ||
                minimumOrderAmount.compareTo(BigDecimal.valueOf(100000)) > 0) {
            throw new CouponMinimumOrderAmountException();
        }
    }

    private void validateDiscountRate() {
        int discountRate = discountAmount.multiply(BigDecimal.valueOf(100))
                .divide(minimumOrderAmount, 0, RoundingMode.DOWN)
                .intValue();
        if (discountRate < 3 || discountRate > 20) {
            throw new CouponDiscountRateException(discountRate);
        }
    }
}
