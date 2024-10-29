package coupon.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PricePolicy {

    private static final long DISCOUNT_AMOUNT_UNIT = 500L;
    private static final long DISCOUNT_AMOUNT_MIN = 1_000L;
    private static final long DISCOUNT_AMOUNT_MAX = 10_000L;

    private static final long PURCHASE_PRICE_MIN = 5_000L;
    private static final long PURCHASE_PRICE_MAX = 100_000L;

    private static final int DISCOUNT_RATE_MIN = 3;
    private static final int DISCOUNT_RATE_MAX = 20;

    public void validate(long discountAmount, long purchaseAmount) {
        validateDiscountAmount(discountAmount);
        validatePurchaseAmount(purchaseAmount);
        validateDiscountRate(discountAmount, purchaseAmount);
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount < DISCOUNT_AMOUNT_MIN || discountAmount > DISCOUNT_AMOUNT_MAX) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원 이상, %d원 이하여야 합니다.", DISCOUNT_AMOUNT_MIN, DISCOUNT_AMOUNT_MAX));
        }
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원 단위로 설정할 수 있습니다.", DISCOUNT_AMOUNT_UNIT));
        }
    }

    private void validatePurchaseAmount(long purchaseAmount) {
        if (purchaseAmount < PURCHASE_PRICE_MIN || purchaseAmount > PURCHASE_PRICE_MAX) {
            throw new IllegalArgumentException(
                    String.format("최소 주문 금액은 %d원 이상 %d원 이하여야 합니다.", PURCHASE_PRICE_MIN, PURCHASE_PRICE_MAX));
        }
    }

    private void validateDiscountRate(long discountAmount, long purchaseAmount) {
        long purchaseRate = discountAmount * 100 / purchaseAmount;
        if (purchaseRate < DISCOUNT_RATE_MIN || purchaseRate > DISCOUNT_RATE_MAX) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %d%% 이상, %d%% 이하여야 합니다.", DISCOUNT_RATE_MIN, DISCOUNT_RATE_MAX));
        }
    }
}
