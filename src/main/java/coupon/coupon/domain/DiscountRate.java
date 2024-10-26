package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiscountRate(Integer discountRate) {

    private static final Integer MINIMUM_DISCOUNT_RATE = 3;
    private static final Integer MAXIMUM_DISCOUNT_RATE = 20;

    public DiscountRate {
        validateRateRange(discountRate);
    }

    public static DiscountRate from(Long discountAmount, Long minimumOrderAmount) {
        return new DiscountRate((int) ((double) discountAmount / minimumOrderAmount * 100));
    }

    public void validateRateRange(final Integer discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE || discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하의 값이어야 합니다.");
        }
    }
}
