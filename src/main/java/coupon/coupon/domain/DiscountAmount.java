package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int VALID_REMAINING_DISCOUNT_AMOUNT = 0;

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int VALUE_TO_MAKE_DISCOUNT_RATE = 100;

    private int discountAmount;

    public DiscountAmount(int discountAmount, int minOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || MAX_DISCOUNT_AMOUNT < discountAmount) {
            throw new IllegalArgumentException("할인 금액은 %,d원 이상 ~ %,d원 이하여야 합니다.".formatted(MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }
        if (hasInvalidDiscountAmountUnit(discountAmount)) {
            throw new IllegalArgumentException("할인 금액은 %,d원 단위어야 합니다.".formatted(DISCOUNT_AMOUNT_UNIT));
        }
    }

    private boolean hasInvalidDiscountAmountUnit(int discountAmount) {
        return discountAmount % DISCOUNT_AMOUNT_UNIT != VALID_REMAINING_DISCOUNT_AMOUNT;
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        int discountRate = calculateDiscountRate(discountAmount, minOrderAmount);

        if (discountRate < MIN_DISCOUNT_RATE || MAX_DISCOUNT_RATE < discountRate) {
            throw new IllegalArgumentException("할인율은 %d%% 이상 ~ %d%% 이하여야 합니다.".formatted(MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    private int calculateDiscountRate(int discountAmount, int minOrderAmount) {
        return (discountAmount * VALUE_TO_MAKE_DISCOUNT_RATE) / minOrderAmount;
    }

    public int getValue() {
        return discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountAmount that = (DiscountAmount) o;
        return discountAmount == that.discountAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(discountAmount);
    }
}
