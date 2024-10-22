package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class CouponDiscountAmount {

    private static final int MINIMUM_DISCOUNT_AMOUNT_LIMIT = 1000;
    private static final int MAXIMUM_DISCOUNT_AMOUNT_LIMIT = 10000;
    private static final int ENABLE_DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MINIMUM_DISCOUNT_PERCENTAGE_LIMIT = 3;
    private static final int MAXIMUM_DISCOUNT_PERCENTAGE_LIMIT = 20;

    private final int value;

    public CouponDiscountAmount(final int discountAmount, final CouponMinimumOrderAmount minimumOrderAmount) {
        validateDiscountAmountLimit(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        validateDiscountPercentage(discountAmount, minimumOrderAmount);
        this.value = discountAmount;
    }

    private void validateDiscountAmountLimit(final int discountAmount) {
        if (discountAmount < MINIMUM_DISCOUNT_AMOUNT_LIMIT || discountAmount > MAXIMUM_DISCOUNT_AMOUNT_LIMIT) {
            throw new IllegalArgumentException(
                    "할인 금액은 " + MINIMUM_DISCOUNT_AMOUNT_LIMIT + "원 이상, " + MAXIMUM_DISCOUNT_AMOUNT_LIMIT
                            + "원 이하여햐 합니다. - " + discountAmount);
        }
    }

    private void validateDiscountAmountUnit(final int discountAmount) {
        if (discountAmount % ENABLE_DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(
                    "할인 금액은 " + ENABLE_DISCOUNT_AMOUNT_UNIT + "원 단위로 설정할 수 있습니다. - " + discountAmount);
        }
    }

    private void validateDiscountPercentage(final int discountAmount, final CouponMinimumOrderAmount minimumOrderAmount) {
        final int discountPercentage = calculateDiscountPercentage(discountAmount, minimumOrderAmount);
        if (discountPercentage < MINIMUM_DISCOUNT_PERCENTAGE_LIMIT
                | discountPercentage > MAXIMUM_DISCOUNT_PERCENTAGE_LIMIT) {
            throw new IllegalArgumentException(
                    "할인율은 " + MINIMUM_DISCOUNT_PERCENTAGE_LIMIT + "% 이상, " + MAXIMUM_DISCOUNT_PERCENTAGE_LIMIT
                            + "% 이하만 가능합니다. - " + discountPercentage);
        }
    }

    private int calculateDiscountPercentage(final int discountAmount, final CouponMinimumOrderAmount minimumOrderAmount) {
        return (int) (((double) discountAmount / minimumOrderAmount.getValue()) * 100);
    }
}
