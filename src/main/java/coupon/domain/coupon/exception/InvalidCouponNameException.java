package coupon.domain.coupon.exception;

public class InvalidCouponNameException extends IllegalArgumentException {

    public InvalidCouponNameException() {
        super();
    }

    public InvalidCouponNameException(String s) {
        super(s);
    }

    public InvalidCouponNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCouponNameException(Throwable cause) {
        super(cause);
    }
}
