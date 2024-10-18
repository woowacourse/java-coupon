package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import lombok.Getter;

@Getter
public class DiscountPrice {

    public static final int MINIMUM_PRICE = 1000;
    public static final int MAXIMUM_PRICE = 10000;
    public static final int PRICE_UNIT = 500;

    private final int price;

    public DiscountPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (price < MINIMUM_PRICE || MAXIMUM_PRICE < price) {
            throw new CouponException(ErrorConstant.DISCOUNT_PRICE_NOT_IN_RANGE);
        }
        if (price % PRICE_UNIT != 0) {
            throw new CouponException(ErrorConstant.NOT_AVAILABLE_UNIT_PRICE);
        }
    }
}
