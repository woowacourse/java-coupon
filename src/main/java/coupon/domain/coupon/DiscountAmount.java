package coupon.domain.coupon;

public class DiscountAmount {

    private static final int MINIMUM_OF_AMOUNT = 1000;
    private static final int MAXIMUM_OF_AMOUNT = 10000;
    private static final int DIVISOR = 500;
    private static final int MINIMUM_OF_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_OF_DISCOUNT_RATE = 20;

    private final int amount;

    public DiscountAmount(int amount, int minimumOrderAmount) {
        validate(amount, minimumOrderAmount);
        this.amount = amount;
    }

    private void validate(int amount, int minimumOrderAmount) {
        validateRange(amount);
        validateMultiple(amount);
        int discountRate = (int) (((double) amount / minimumOrderAmount) * 100);
        validateDiscountRate(discountRate);
    }

    private void validateRange(int amount) {
        if (MINIMUM_OF_AMOUNT <= amount && amount <= MAXIMUM_OF_AMOUNT) {
            return;
        }
        throw new IllegalArgumentException("할인 금액은 " + MINIMUM_OF_AMOUNT + "원 이상 " + MAXIMUM_OF_AMOUNT + "원 이하여야 합니다.");
    }

    private void validateMultiple(int amount) {
        if (amount % DIVISOR != 0) {
            throw new IllegalArgumentException("할인 금액은 " + DIVISOR + "원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateDiscountRate(int discountRate) {
        if (MINIMUM_OF_DISCOUNT_RATE <= discountRate && discountRate <= MAXIMUM_OF_DISCOUNT_RATE) {
            return;
        }
        throw new IllegalArgumentException(
                "할인율은 " + MINIMUM_OF_DISCOUNT_RATE + "% 이상 " + MAXIMUM_OF_DISCOUNT_RATE + "% 이하여야 합니다.");
    }
}
