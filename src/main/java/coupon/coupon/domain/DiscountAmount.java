package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record DiscountAmount(Long discountAmount) {

    private static final Long MINIMUM_DISCOUNT_AMOUNT = 1_000L;
    private static final Long MAXIMUM_DISCOUNT_AMOUNT = 10_000L;
    private static final Long DISCOUNT_AMOUNT_UNIT = 500L;

    public DiscountAmount {
        validateDiscountAmountNull(discountAmount);
        validateDiscountAmountRange(discountAmount);
        validateDiscountUnit(discountAmount);
    }

    private void validateDiscountAmountNull(final Long discountAmount) {
        if (discountAmount == null) {
            throw new IllegalArgumentException("할인 금액은 null이 될 수 없습니다.");
        }
    }

    private void validateDiscountAmountRange(final Long discountAmount) {
        if (discountAmount > MAXIMUM_DISCOUNT_AMOUNT || discountAmount < MINIMUM_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(String.format("할인 금액은 %,d원 이상 %,d원 이하만 가능합니다.", MINIMUM_DISCOUNT_AMOUNT, MAXIMUM_DISCOUNT_AMOUNT));
        }
    }

    private void validateDiscountUnit(final Long discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %,d원 단위로만 가능합니다.", DISCOUNT_AMOUNT_UNIT));
        }
    }
}
