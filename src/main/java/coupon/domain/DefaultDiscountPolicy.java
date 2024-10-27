package coupon.domain;

public class DefaultDiscountPolicy implements DiscountPolicy {

    private static final long DISCOUNT_AMOUNT_UNIT = 500L;
    private static final long DISCOUNT_AMOUNT_MIN = 1_000L;
    private static final long DISCOUNT_AMOUNT_MAX = 10_000L;

    private static final long MIN_ORDER_PRICE_MIN = 5_000L;
    private static final long MIN_ORDER_PRICE_MAX = 100_000;

    private static final long DISCOUNT_RATE_MIN = 3L;
    private static final long DISCOUNT_RATE_MAX = 20L;

    @Override
    public void validate(long discountAmount, long minimumOrderPrice) {
        requireDiscountAmountInBound(discountAmount);
        requireDiscountAmountUnitMatch(discountAmount);
        requireMinimumOrderPriceInBound(minimumOrderPrice);
        requireDiscountRateInBound(discountAmount, minimumOrderPrice);
    }

    private void requireDiscountAmountInBound(long discountAmount) {
        boolean isInBound = DISCOUNT_AMOUNT_MIN <= discountAmount && discountAmount <= DISCOUNT_AMOUNT_MAX;
        if (!isInBound) {
            throw new IllegalArgumentException(String.format(
                    "할인 금액은 %d원 이상, %d원 이하여야 합니다.",
                    DISCOUNT_AMOUNT_MIN,
                    DISCOUNT_AMOUNT_MAX));
        }
    }

    private void requireDiscountAmountUnitMatch(long discountAmount) {
        boolean isUnitMatch = discountAmount % DISCOUNT_AMOUNT_UNIT == 0;
        if (!isUnitMatch) {
            throw new IllegalArgumentException(String.format(
                    "할인 금액은 %d원 단위로 설정할 수 있습니다.",
                    DISCOUNT_AMOUNT_UNIT));
        }
    }

    private void requireMinimumOrderPriceInBound(long minimumOrderPrice) {
        boolean isInBound = MIN_ORDER_PRICE_MIN <= minimumOrderPrice && minimumOrderPrice <= MIN_ORDER_PRICE_MAX;
        if (!isInBound) {
            throw new IllegalArgumentException(String.format(
                    "최소 주문 금액은 %d원 이상, %d원 이하여야 합니다.",
                    MIN_ORDER_PRICE_MIN,
                    MIN_ORDER_PRICE_MAX));
        }
    }

    private void requireDiscountRateInBound(long discountAmount, long minimumOrderPrice) {
        long discountRate = 100 * discountAmount / minimumOrderPrice;
        boolean isRateValid = DISCOUNT_RATE_MIN <= discountRate && discountRate <= DISCOUNT_RATE_MAX;
        if (!isRateValid) {
            throw new IllegalArgumentException(String.format(
                    "할인율은 %d%% 이상, %d%% 이하여야 합니다. 현재 할인율: %d%%",
                    DISCOUNT_RATE_MIN,
                    DISCOUNT_RATE_MAX,
                    discountRate));
        }
    }
}
