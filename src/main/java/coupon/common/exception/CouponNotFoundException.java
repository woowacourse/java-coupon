package coupon.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CouponNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당하는 쿠폰을 찾을 수 없습니다.";

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public CouponNotFoundException() {
        super(MESSAGE);
    }
}
