package coupon.domain.coupon;

import jakarta.persistence.Embeddable;

@Embeddable
public class SaleOrderPrice {

    private static final int MIN_SALE_PRICE = 5000;
    private static final int MAX_SALE_PRICE = 10000;

    private int price;

    public SaleOrderPrice(int price) {
        validatePrice(price);
        this.price = price;
    }

    protected SaleOrderPrice() {
    }

    private void validatePrice(int price) {
        if (price < MIN_SALE_PRICE || price > MAX_SALE_PRICE) {
            throw new IllegalArgumentException("최소 주문금액은 " + MIN_SALE_PRICE + "이상 " + MAX_SALE_PRICE + "이하여야 합니다");
        }
    }

    public int getPrice() {
        return price;
    }
}
