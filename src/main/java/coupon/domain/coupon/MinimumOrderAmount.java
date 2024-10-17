package coupon.domain.coupon;

public class MinimumOrderAmount {

    private static final int MINIMUM_OF_AMOUNT = 5000;
    private static final int MAXIMUM_OF_AMOUNT = 100000;

    private final int amount;

    public MinimumOrderAmount(int amount) {
        validateRange(amount);
        this.amount = amount;
    }

    private void validateRange(int amount) {
        if (MINIMUM_OF_AMOUNT <= amount && amount <= MAXIMUM_OF_AMOUNT) {
            return;
        }
        throw new IllegalArgumentException(
                "최소 주문 금액은 " + MINIMUM_OF_AMOUNT + "원 이상 " + MAXIMUM_OF_AMOUNT + "원 이하여야 합니다.");
    }
}
