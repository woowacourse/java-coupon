package coupon;

public class Coupon {

    private final String name;
    private final Integer discountAmount;
    private final Integer purchaseAmount;

    public Coupon(String name, Integer discountAmount, Integer purchaseAmount) {
        validate(name, discountAmount, purchaseAmount);
        this.name = name;
        this.discountAmount = discountAmount;
        this.purchaseAmount = purchaseAmount;
    }

    private void validate(String name, Integer discountAmount, Integer purchaseAmount) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validatePurchaseAmount(purchaseAmount);
        validateDiscountRate(discountAmount, purchaseAmount);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw new IllegalArgumentException("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
        }
    }

    private static void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount == null || discountAmount < 1_000 || discountAmount > 10_000) {
            throw new IllegalArgumentException("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private static void validatePurchaseAmount(Integer purchaseAmount) {
        if (purchaseAmount == null || purchaseAmount < 5_000 || purchaseAmount > 100_000) {
            throw new IllegalArgumentException("최소 주문 금액은 5_000원 이상 100_000원 이하여야 합니다.");
        }
    }

    private static void validateDiscountRate(Integer discountAmount, Integer purchaseAmount) {
        int purchaseRate = discountAmount * 100 / purchaseAmount;
        if (purchaseRate < 3 || purchaseRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }
}
