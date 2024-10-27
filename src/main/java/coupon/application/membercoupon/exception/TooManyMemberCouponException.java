package coupon.application.membercoupon.exception;

public class TooManyMemberCouponException extends IllegalStateException {

    public TooManyMemberCouponException() {
        super();
    }

    public TooManyMemberCouponException(String s) {
        super(s);
    }

    public TooManyMemberCouponException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyMemberCouponException(Throwable cause) {
        super(cause);
    }
}
