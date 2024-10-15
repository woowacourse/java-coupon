package coupon.exception;

import org.springframework.http.HttpStatus;

public class CouponException extends RuntimeException {

    private final ExceptionType exceptionType;

    public CouponException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public CouponException(ExceptionType exceptionType, Throwable cause) {
        super(exceptionType.getMessage(), cause);
        this.exceptionType = exceptionType;
    }

    public HttpStatus getStatus() {
        return exceptionType.getStatus();
    }
}
