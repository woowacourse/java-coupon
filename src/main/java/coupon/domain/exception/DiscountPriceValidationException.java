package coupon.domain.exception;

public class DiscountPriceValidationException extends CouponException {
    public DiscountPriceValidationException() {
        super();
    }

    public DiscountPriceValidationException(String message) {
        super(message);
    }

    public DiscountPriceValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountPriceValidationException(Throwable cause) {
        super(cause);
    }

    public DiscountPriceValidationException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
