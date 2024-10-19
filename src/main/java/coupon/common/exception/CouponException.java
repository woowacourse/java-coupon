package coupon.common.exception;

import coupon.common.ErrorConstant;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CouponException extends RuntimeException {

    private final ErrorConstant errorConstant;

    public CouponException(ErrorConstant error) {
        super(error.getMessage());
        this.errorConstant = error;
    }

    public String getMessage() {
        return errorConstant.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return errorConstant.getHttpStatus();
    }
}
