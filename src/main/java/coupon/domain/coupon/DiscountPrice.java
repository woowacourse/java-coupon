package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DiscountPrice {
    private static final int MIN_DISCOUNT_PRICE = 1000;
    private static final int MAX_DISCOUNT_PRICE = 10000;
    private static final int UNIT_DISCOUNT_PRICE = 500;

    @Column(name = "discount_price", nullable = false)
    private int price;

    public DiscountPrice(int price) {
        validatePriceRange(price);
        validatePriceUnit(price);
        this.price = price;
    }

    protected DiscountPrice() {
    }

    private void validatePriceRange(int price) {
        if (price < MIN_DISCOUNT_PRICE || price > MAX_DISCOUNT_PRICE) {
            throw new IllegalArgumentException("price는 " + MIN_DISCOUNT_PRICE + "이상 " + MAX_DISCOUNT_PRICE + "이하여야 합니다");
        }
    }

    private void validatePriceUnit(int price) {
        if (price % UNIT_DISCOUNT_PRICE != 0) {
            throw new IllegalArgumentException("price는 " + UNIT_DISCOUNT_PRICE + "단위로 설정할 수 있습니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
