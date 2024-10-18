package coupon.exception;

public class CouponIssueDateException extends CouponException {
    public CouponIssueDateException() {
        this("쿠폰 발급 가능일이 아닙니다.");
    }

    public CouponIssueDateException(String message) {
        super(message);
    }

    public CouponIssueDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponIssueDateException(Throwable cause) {
        super(cause);
    }

    public CouponIssueDateException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
