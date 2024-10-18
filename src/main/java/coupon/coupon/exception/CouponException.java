package coupon.coupon.exception;

public class CouponException extends RuntimeException {
    public CouponException(final String message) {
        super(message);
    }

    public CouponException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
