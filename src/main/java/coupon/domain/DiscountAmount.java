package coupon.domain;

public class DiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT_RANGE = 1000;
    private static final int MAX_DISCOUNT_AMOUNT_RANGE = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;

    private final int amount;

    public DiscountAmount(int amount) {
        validateRange(amount);
        validateUnit(amount);
        this.amount = amount;
    }

    private void validateRange(int amount) {
        if (amount < MIN_DISCOUNT_AMOUNT_RANGE || amount > MAX_DISCOUNT_AMOUNT_RANGE) {
            throw new IllegalArgumentException("유효하지 않은 할인 금액입니다.");
        }
    }

    private void validateUnit(int amount) {
        if (amount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("유효하지 않은 할인 금액 단위입니다.");
        }
    }
}
