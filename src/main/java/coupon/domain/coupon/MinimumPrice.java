package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import lombok.Getter;

@Getter
public class MinimumPrice {

    public static final int MINIMUM_PRICE = 5000;
    public static final int MAXIMUM_PRICE = 100000;

    private final int price;

    public MinimumPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (price < MINIMUM_PRICE || MAXIMUM_PRICE < price) {
            throw new CouponException(ErrorConstant.DISCOUNT_PRICE_NOT_IN_RANGE);
        }
    }
}
