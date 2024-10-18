package coupon.exception;

public class InvalidCouponNameException extends RuntimeException {

    public InvalidCouponNameException(final String message) {
        super(message);
    }
}
