package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiscountRate(Double discountRate) {

    private static final double MINIMUM_DISCOUNT_RATE = 3;
    private static final double MAXIMUM_DISCOUNT_RATE = 20;

    public DiscountRate {
        validateRateRange(discountRate);
    }

    public void validateRateRange(final Double discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE || discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하의 값이어야 합니다.");
        }
    }
}
