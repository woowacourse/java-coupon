package coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class DiscountAmount {

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 10000;
    private static final int AMOUNT_UNIT = 500;

    private int amount;

    protected DiscountAmount() {
    }

    public DiscountAmount(int amount) {
        validateRange(amount);
        validateUnit(amount);
        this.amount = amount;
    }

    private void validateRange(int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(String.format("할인 금액은 %,d원 이상 %,d원 이하여야 합니다.", MIN_AMOUNT, MAX_AMOUNT));
        }
    }

    private void validateUnit(int amount) {
        if (amount % AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 단위로 설정할 수 있습니다.", AMOUNT_UNIT));
        }
    }

    public int getAmount() {
        return amount;
    }
}
