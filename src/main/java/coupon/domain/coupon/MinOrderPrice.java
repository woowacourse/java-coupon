package coupon.domain.coupon;

import coupon.domain.exception.CouponNameValidationException;
import coupon.domain.exception.MinOrderPriceValidationException;
import jakarta.persistence.Embeddable;

@Embeddable
public class MinOrderPrice {
    private static final int MIN_MIN_ORDER_PRICE = 5_000;
    private static final int MAX_MIN_ORDER_PRICE = 10_000;

    private int minOrderPrice;

    public MinOrderPrice(int minOrderPrice) {
        validate(minOrderPrice);
        this.minOrderPrice = minOrderPrice;
    }

    public MinOrderPrice() {
    }

    private void validate(int minOrderPrice) {
        if(minOrderPrice < MIN_MIN_ORDER_PRICE || minOrderPrice > MAX_MIN_ORDER_PRICE) {
            throw new MinOrderPriceValidationException(MIN_MIN_ORDER_PRICE, MAX_MIN_ORDER_PRICE);
        }
    }
}
