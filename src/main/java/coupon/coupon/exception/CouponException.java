package coupon.coupon.exception;

public class CouponException extends RuntimeException{

    private final CouponErrorMessage errorMessage;

    public CouponException(CouponErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CouponErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
