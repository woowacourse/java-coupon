package coupon.coupon.service;

import org.springframework.stereotype.Component;

@Component
public class CouponValidator {

    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int VALID_REMAINING_DISCOUNT_AMOUNT = 0;

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int VALUE_TO_MAKE_DISCOUNT_RATE = 100;

    private static final int MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_ORDER_AMOUNT = 100_000;

    public void validateAmount(int discountAmount, int minOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        validateOrderAmount(minOrderAmount);
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

    private void validateOrderAmount(int orderAmount) {
        if (orderAmount < MIN_ORDER_AMOUNT || MAX_ORDER_AMOUNT < orderAmount) {
            throw new IllegalArgumentException("최소 주문 금액은 %,d원 이상 ~ %,d원 이하여야 합니다.".formatted(MIN_ORDER_AMOUNT, MAX_ORDER_AMOUNT));
        }
    }
}
