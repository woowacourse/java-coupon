package coupon.coupon.exception;

public class CouponApplicationException extends RuntimeException {
    public CouponApplicationException(final String message) {
        super(message);
    }

    public CouponApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
