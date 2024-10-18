package coupon.exception;

public class CouponNameValidationException extends CouponException {
    public CouponNameValidationException() {
        super();
    }

    public CouponNameValidationException(String message) {
        super(message);
    }

    public CouponNameValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponNameValidationException(Throwable cause) {
        super(cause);
    }

    public CouponNameValidationException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
