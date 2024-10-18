package coupon.domain;

public class DefaultDiscountPolicy implements DiscountPolicy {

    private static final long UNIT_AMOUNT = 500L;
    private static final long MIN_AMOUNT = 1_000L;
    private static final long MAX_AMOUNT = 10_000L;

    private static final long DISCOUNT_RATE_MIN = 3L;
    private static final long DISCOUNT_RATE_MAX = 20L;

    @Override
    public void validate(long discountAmount, long minimumOrderPrice) {
        requireDiscountAmountInBound(discountAmount);
        requireDiscountAmountUnitMatch(discountAmount);
        requireDiscountRateInBound(discountAmount, minimumOrderPrice);
    }

    private void requireDiscountAmountInBound(long discountAmount) {
        boolean isInBound = MIN_AMOUNT <= discountAmount && discountAmount <= MAX_AMOUNT;
        if (!isInBound) {
            throw new IllegalArgumentException(String.format(
                    "할인 금액은 %d원 이상, %d원 이하여야 합니다.",
                    MIN_AMOUNT,
                    MAX_AMOUNT));
        }
    }

    private void requireDiscountAmountUnitMatch(long discountAmount) {
        boolean isUnitMatch = discountAmount % UNIT_AMOUNT == 0;
        if (!isUnitMatch) {
            throw new IllegalArgumentException(String.format(
                    "할인 금액은 %d원 단위로 설정할 수 있습니다.",
                    UNIT_AMOUNT));
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
