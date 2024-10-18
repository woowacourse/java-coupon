package coupon.exception;


import org.springframework.http.HttpStatus;

public class CouponException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CouponException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CouponException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public CouponException(CouponErrorMessage couponErrorMessage) {
        super(couponErrorMessage.getMessage());
        this.httpStatus = couponErrorMessage.getHttpStatus();
    }
}
