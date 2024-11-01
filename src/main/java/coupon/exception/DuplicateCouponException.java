package coupon.exception;

public class DuplicateCouponException extends RuntimeException {

    public DuplicateCouponException(final String message) {
        super(message);
    }
}
