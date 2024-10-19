package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;

public class PriceInformation {

    private static final int MAXIMUM_DISCOUNT_RATE = 20;
    private static final int MINIMUM_DISCOUNT_RATE = 3;

    private final DiscountPrice discountPrice;
    private final MinimumPrice minimumPrice;

    public PriceInformation(int discountPrice, int minimumPrice) {
        validateDiscountRate(discountPrice, minimumPrice);

        this.discountPrice = new DiscountPrice(discountPrice);
        this.minimumPrice = new MinimumPrice(minimumPrice);
    }

    private void validateDiscountRate(int discountPrice, int minimumPrice) {
        int rate = minimumPrice / discountPrice;

        if (rate < MINIMUM_DISCOUNT_RATE || MAXIMUM_DISCOUNT_RATE < rate) {
            throw new CouponException(ErrorConstant.COUPON_PRICE_RATE_NOT_IN_RANGE);
        }
    }

    public int getDiscountPrice() {
        return discountPrice.getPrice();
    }

    public int getMinimumPrice() {
        return minimumPrice.getPrice();
    }
}
