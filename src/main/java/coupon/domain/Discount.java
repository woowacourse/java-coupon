package coupon.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Discount {

    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_MINIMUM_ORDER_AMOUNT = 5000;
    private static final int MAX_MINIMUM_ORDER_AMOUNT = 100000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    private final int discountAmount;
    private final int minOrderAmount;

    public Discount(int discountAmount, int minOrderAmount) {
        validateDiscount(discountAmount, minOrderAmount);
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
    }

    private void validateDiscount(int discountAmount, int minOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(String.format("Discount Amount cannot be less than %d or more than %d", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("Discount Amount must be multiple of %d", DISCOUNT_AMOUNT_UNIT));
        }
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("Minimum Order Amount cannot be less than %d or more than %d", MIN_MINIMUM_ORDER_AMOUNT, MAX_MINIMUM_ORDER_AMOUNT));
        }
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        double discountRate = (double) 100 * discountAmount / minOrderAmount;
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(String.format("Discount Rate cannot be less than %d%% or more than %d%%", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    public double getDiscountRate() {
        return (double) 100 * discountAmount / minOrderAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount discount)) return false;
        return discountAmount == discount.discountAmount && minOrderAmount == discount.minOrderAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount, minOrderAmount);
    }
}
