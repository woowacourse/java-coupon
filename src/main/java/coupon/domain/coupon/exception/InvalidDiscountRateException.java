package coupon.domain.coupon.exception;

public class InvalidDiscountRateException extends IllegalArgumentException {

    public InvalidDiscountRateException() {
        super();
    }

    public InvalidDiscountRateException(String s) {
        super(s);
    }

    public InvalidDiscountRateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDiscountRateException(Throwable cause) {
        super(cause);
    }
}
