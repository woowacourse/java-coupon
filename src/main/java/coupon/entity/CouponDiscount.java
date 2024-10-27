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

    private static final BigDecimal MIN_DISCOUNT_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal MAX_DISCOUNT_AMOUNT = BigDecimal.valueOf(10000);
    private static final BigDecimal DISCOUNT_AMOUNT_INCREMENT = BigDecimal.valueOf(500);
    private static final BigDecimal MIN_ORDER_AMOUNT = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_ORDER_AMOUNT = BigDecimal.valueOf(100000);
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

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
        if (discountAmount.compareTo(MIN_DISCOUNT_AMOUNT) < 0 ||
                discountAmount.compareTo(MAX_DISCOUNT_AMOUNT) > 0 ||
                discountAmount.remainder(DISCOUNT_AMOUNT_INCREMENT).compareTo(BigDecimal.ZERO) != 0) {
            throw new CouponDiscountAmountException(discountAmount);
        }
    }

    private void validateMinimumOrderAmount() {
        if (minimumOrderAmount.compareTo(MIN_ORDER_AMOUNT) < 0 ||
                minimumOrderAmount.compareTo(MAX_ORDER_AMOUNT) > 0) {
            throw new CouponMinimumOrderAmountException(minimumOrderAmount);
        }
    }

    private void validateDiscountRate() {
        int discountRate = discountAmount.multiply(BigDecimal.valueOf(100))
                .divide(minimumOrderAmount, 0, RoundingMode.DOWN)
                .intValue();
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new CouponDiscountRateException(discountRate);
        }
    }
}

