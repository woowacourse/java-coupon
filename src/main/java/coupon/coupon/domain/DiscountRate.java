package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiscountRate(int rate) {

    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    public DiscountRate {
        validateRateRange(rate);
    }

    public void validateRateRange(int rate) {
        if (rate < MINIMUM_DISCOUNT_RATE || rate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하의 값이어야 합니다.");
        }
    }
}
