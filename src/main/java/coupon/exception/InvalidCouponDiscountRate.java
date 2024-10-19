package coupon.exception;

public class InvalidCouponDiscountRate extends RuntimeException {

    public InvalidCouponDiscountRate(final String message) {
        super(message);
    }
}
