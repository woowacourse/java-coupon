package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponDiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT_SIZE = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT_SIZE = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Column(name = "discount_amount", nullable = false)
    private int value;

    public CouponDiscountAmount(int discountAmount, CouponMinOrderAmount minOrderAmount) {
        validate(discountAmount, minOrderAmount);

        this.value = discountAmount;
    }

    public void validate(int discountAmount, CouponMinOrderAmount minOrderAmount) {
        validateDiscountAmountSize(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
    }

    public void validateDiscountAmountSize(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT_SIZE || discountAmount > MAX_DISCOUNT_AMOUNT_SIZE) {
            String message = "할인 금액은 %,d원 이상 %,d원 이하만 가능합니다."
                    .formatted(MIN_DISCOUNT_AMOUNT_SIZE, MAX_DISCOUNT_AMOUNT_SIZE);

            throw new IllegalArgumentException(message);
        }
    }

    private void validateDiscountAmountUnit(int discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            String message = "할인 금액은 %,d원 단위로만 가능합니다.".formatted(DISCOUNT_AMOUNT_UNIT);

            throw new IllegalArgumentException(message);
        }
    }

    private void validateDiscountRate(int discountAmount, CouponMinOrderAmount minOrderAmount) {
        int discountRate = calculateDiscountRate(discountAmount, minOrderAmount);
        if (discountRate < MIN_DISCOUNT_RATE || MAX_DISCOUNT_RATE < discountRate) {
            String message = "할인율은 %d%% 이상 %d%% 이하만 가능합니다.".formatted(MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE);

            throw new IllegalArgumentException(message);
        }
    }

    private int calculateDiscountRate(int discountAmount, CouponMinOrderAmount minOrderAmount) {
        return (int) ((discountAmount / (double) minOrderAmount.getValue()) * 100);
    }
}
