package coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class DiscountRate {

    private static final int PERCENTAGE_MULTIPLIER = 100;
    private static final int MIN_RATE = 3;
    private static final int MAX_RATE = 20;

    private int discountRate;

    protected DiscountRate() {
    }

    public DiscountRate(int discountRate) {
        validateRange(discountRate);
        this.discountRate = discountRate;
    }

    public DiscountRate(DiscountAmount discountAmount, MinOrderPrice minOrderPrice) {
        this((discountAmount.getAmount() * PERCENTAGE_MULTIPLIER) / minOrderPrice.getPrice());
    }

    private void validateRange(int discountRate) {
        if (discountRate < MIN_RATE || discountRate > MAX_RATE) {
            throw new IllegalArgumentException(String.format("할인율은 %d%% 이상 %d%% 이하여야 합니다.", MIN_RATE, MAX_RATE));
        }
    }

    public int getDiscountRate() {
        return discountRate;
    }
}
