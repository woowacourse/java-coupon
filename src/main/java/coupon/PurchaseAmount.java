package coupon;

public class PurchaseAmount {

    private final Integer purchaseAmount;

    public PurchaseAmount(Integer purchaseAmount) {
        validate(purchaseAmount);
        this.purchaseAmount = purchaseAmount;
    }

    private static void validate(Integer purchaseAmount) {
        if (purchaseAmount == null || purchaseAmount < 5_000 || purchaseAmount > 100_000) {
            throw new IllegalArgumentException("최소 주문 금액은 5_000원 이상 100_000원 이하여야 합니다.");
        }
    }

}
