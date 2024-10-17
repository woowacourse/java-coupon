package coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiscountAmount(Long amount) {

    private static final long MINIMUM_DISCOUNT_AMOUNT = 1_000;
    private static final long MAXIMUM_DISCOUNT_AMOUNT = 10_000;
    private static final long DISCOUNT_AMOUNT_UNIT = 500;

    public DiscountAmount {
        validateDiscountable(amount);
    }

    public void validateDiscountable(final Long price) {
        validateDiscountAmountRange(price);
        validateDiscountUnit(price);
    }

    public void validateDiscountAmountRange(final Long price) {
        if (price > MAXIMUM_DISCOUNT_AMOUNT || price < MINIMUM_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하만 가능합니다.");
        }
    }

    public void validateDiscountUnit(final Long price) {
        if (price % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로만 가능합니다.");
        }
    }
}
