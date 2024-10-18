package coupon.domain;

public class MinimumOrderPrice {

    private static final int MIN_AMOUNT = 5000;
    private static final int MAX_AMOUNT = 100_000;

    private final int amount;

    public MinimumOrderPrice(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("유효하지 않은 최소 주문 금액입니다.");
        }
    }

    public int getAmount() {
        return amount;
    }
}
