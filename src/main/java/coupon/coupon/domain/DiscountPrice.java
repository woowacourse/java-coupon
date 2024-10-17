package coupon.coupon.domain;

public record DiscountPrice(int price) {

    private static final int MIN_LENGTH = 1_000;
    private static final int MAX_LENGTH = 10_000;
    private static final int UNIT = 500;

    public DiscountPrice {
        validate(price);
    }

    private void validate(int discountPrice) {
        if (discountPrice < MIN_LENGTH) {
            throw new IllegalArgumentException("할인 금액은 " + MIN_LENGTH + "원 이상이어야 한다.");
        }
        if (discountPrice > MAX_LENGTH) {
            throw new IllegalArgumentException("할인 금액은 " + MAX_LENGTH + "원 이하여야 한다.");
        }
        if (discountPrice % UNIT == 0) {
            throw new IllegalArgumentException("할인 금액은 " + UNIT + "원 단위로 설정할 수 있다.");
        }
    }
}
