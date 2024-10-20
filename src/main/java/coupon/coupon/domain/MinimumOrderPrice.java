package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class MinimumOrderPrice {

    private static final int MIN_LENGTH = 5_000;
    private static final int MAX_LENGTH = 100_000;
    private final int price;

    public MinimumOrderPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int minimumOrderPrice) {
        if (minimumOrderPrice < MIN_LENGTH) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MIN_LENGTH + "원 이상이어야 한다.");
        }
        if (minimumOrderPrice > MAX_LENGTH) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MAX_LENGTH + "원 이하여야 한다.");
        }
    }
}
