package coupon.application.coupon.exception;

public class CouponNotFoundException extends IllegalStateException {

    public CouponNotFoundException() {
        super();
    }

    public CouponNotFoundException(String s) {
        super(s);
    }

    public CouponNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponNotFoundException(Throwable cause) {
        super(cause);
    }
}
