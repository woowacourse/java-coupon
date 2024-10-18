package coupon.exception;

public class NotFoundCouponException extends RuntimeException {

    public NotFoundCouponException(final String message) {
        super(message);
    }
}
