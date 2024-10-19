package coupon.exception;

public class InvalidCouponDiscountAmount extends RuntimeException {

    public InvalidCouponDiscountAmount(final String message) {
        super(message);
    }
}
