package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiscountRate(Integer discountRate) {

    private static final Integer MINIMUM_DISCOUNT_RATE = 3;
    private static final Integer MAXIMUM_DISCOUNT_RATE = 20;

    public DiscountRate {
        validateDiscountRateNull(discountRate);
        validateRateRange(discountRate);
    }

    private void validateDiscountRateNull(final Integer discountRate) {
        if (discountRate == null) {
            throw new IllegalArgumentException("할인율은 null이 될 수 없습니다.");
        }
    }

    public static DiscountRate from(final Long discountAmount, final Long minimumOrderAmount) {
        return new DiscountRate((int) ((double) discountAmount / minimumOrderAmount * 100));
    }

    public void validateRateRange(final Integer discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE || discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException(String.format("할인율은 %,d%% 이상 %,d%% 이하의 값이어야 합니다.", MINIMUM_DISCOUNT_RATE, MAXIMUM_DISCOUNT_RATE));
        }
    }
}
