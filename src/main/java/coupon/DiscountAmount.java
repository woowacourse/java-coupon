package coupon;

public class DiscountAmount {

    private final Integer discountAmount;

    public DiscountAmount(Integer discountAmount) {
        validate(discountAmount);
        this.discountAmount = discountAmount;
    }

    private static void validate(Integer discountAmount) {
        if (discountAmount == null || discountAmount < 1_000 || discountAmount > 10_000) {
            throw new IllegalArgumentException("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }
}
