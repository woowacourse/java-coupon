package coupon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CouponErrorMessage {

    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "Coupon not found"),
    FAILED_TO_READ(HttpStatus.SERVICE_UNAVAILABLE, "Failed to read data"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
