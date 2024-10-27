package coupon.domain.coupon;

import coupon.exception.CouponConstraintViolationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import lombok.Getter;

@Getter
class Accounting {
    private static final int DISCOUNT_AMOUNT_LOWER_BOUND = 1_000;
    private static final int DISCOUNT_AMOUNT_UPPER_BOUND = 10_000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MINIMUM_ORDER_COST_LOWER_BOUND = 5_000;
    private static final int MINIMUM_ORDER_COST_UPPER_BOUND = 100_000;
    private static final BigDecimal DISCOUNT_RATE_LOWER_BOUND = BigDecimal.valueOf(3);
    private static final BigDecimal DISCOUNT_RATE_UPPER_BOUND = BigDecimal.valueOf(20);

    private final int discountAmount;
    private final int minimumOrderCost;

    public Accounting(int discountAmount, int minimumOrderCost) {
        this.discountAmount = discountAmount;
        this.minimumOrderCost = minimumOrderCost;
        validate(discountAmount, minimumOrderCost);
    }

    private void validate(int discountAmount, int minimumOrderCost) {
        if (discountAmount < DISCOUNT_AMOUNT_LOWER_BOUND || discountAmount > DISCOUNT_AMOUNT_UPPER_BOUND) {
            throw new CouponConstraintViolationException(
                    String.format("discount quantity: %d, out of bound", discountAmount));
        }
        if (minimumOrderCost < MINIMUM_ORDER_COST_LOWER_BOUND || minimumOrderCost > MINIMUM_ORDER_COST_UPPER_BOUND) {
            throw new CouponConstraintViolationException(
                    String.format("minimum order cost: %d, out of bound", minimumOrderCost));
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new CouponConstraintViolationException(
                    String.format("discount quantity error: %d,  must match min discount unit", discountAmount));
        }

        int discountRate = getDiscountRate();

        if (discountRate < DISCOUNT_RATE_LOWER_BOUND.intValue()
            || discountRate > DISCOUNT_RATE_UPPER_BOUND.intValue()) {
            throw new CouponConstraintViolationException(
                    String.format("discount rate out of bound: %d", discountRate));
        }
    }

    public int getDiscountRate() {
        return BigDecimal.valueOf(discountAmount)
                .multiply(new BigDecimal(100))
                .divide(BigDecimal.valueOf(minimumOrderCost), RoundingMode.DOWN)
                .intValue();
    }

}
