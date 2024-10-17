package coupon;

public class Coupon {

    private final String name;
    private final Integer discountAmount;

    public Coupon(String name, Integer discountAmount) {
        validate(name, discountAmount);
        this.name = name;
        this.discountAmount = discountAmount;
    }

    private void validate(String name, Integer discountAmount) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw new IllegalArgumentException("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
        }
        if (discountAmount == null || discountAmount < 1000 || discountAmount > 10000) {
            throw new IllegalArgumentException("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }
}
