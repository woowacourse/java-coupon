package coupon.domain;

public class DiscountRateCalculator {

    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    private final long orderPrice;
    private final long discountAmount;

    public DiscountRateCalculator(final long orderPrice, final long discountAmount) {
        this.orderPrice = orderPrice;
        this.discountAmount = discountAmount;
    }

    public long calculate() {
        return Math.floorDiv(orderPrice, discountAmount);
    }

    public void validateRate() {
        final long discountRate = calculate();
        validateMinRate(discountRate);
        validateMaxRate(discountRate);
    }

    private void validateMinRate(final long discountAmount) {
        if (discountAmount < MINIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인률은 %d%%이상이어야합니다.".formatted(MINIMUM_DISCOUNT_RATE));
        }
    }

    private void validateMaxRate(final long discountAmount) {
        if (discountAmount > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인률은 %d%%이하이어야합니다.".formatted(MAXIMUM_DISCOUNT_RATE));
        }
    }
}
