package coupon.exception;

public class MemberCouponIssueLimitException extends CouponException {
    public MemberCouponIssueLimitException() {
        super("이미 발급 가능한 모든 쿠폰을 발급하였습니다.");
    }

    public MemberCouponIssueLimitException(String message) {
        super(message);
    }

    public MemberCouponIssueLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberCouponIssueLimitException(Throwable cause) {
        super(cause);
    }

    public MemberCouponIssueLimitException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
