package coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class MinOrderPrice {

    private static final int MIN_VALUE = 5000;
    private static final int MAX_VALUE = 100000;

    private int price;

    protected MinOrderPrice() {
    }

    public MinOrderPrice(int price) {
        validateRange(price);
        this.price = price;
    }

    private void validateRange(int price) {
        if (price < MIN_VALUE || price > MAX_VALUE) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
