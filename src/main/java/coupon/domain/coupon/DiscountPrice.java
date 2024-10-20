package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DiscountPrice {
    private static final int MIN_SALE_PRICE = 1000;
    private static final int MAX_SALE_PRICE = 10000;
    private static final int UNIT_SALE_PRICE = 500;

    @Column(name = "discount_price", nullable = false)
    private int price;

    public DiscountPrice(int price) {
        validatePrice(price);
        this.price = price;
    }

    protected DiscountPrice() {
    }

    void validatePrice(int price) {
        if (price < MIN_SALE_PRICE || price > MAX_SALE_PRICE) {
            throw new IllegalArgumentException("price는 " + MIN_SALE_PRICE + "이상 " + MAX_SALE_PRICE + "이하여야 합니다");
        }

        if (price % UNIT_SALE_PRICE != 0) {
            throw new IllegalArgumentException("price는 " + UNIT_SALE_PRICE + "단위로 설정할 수 있습니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
