package coupon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CouponErrorMessage {

    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "Coupon not found"),
    FAILED_TO_READ(HttpStatus.SERVICE_UNAVAILABLE, "Failed to read data"),
    EXCEEDED_MAX_COUPON_COUNT(HttpStatus.BAD_REQUEST, "Exceeded max coupon count"),
    COUPON_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "Coupon is not available for issuing"),
    COUPON_NAME_EMPTY(HttpStatus.BAD_REQUEST, "Coupon name is empty"),
    COUPON_NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "Coupon name is too long"),
    COUPON_INVALID_DISCOUNT_AMOUNT(HttpStatus.BAD_REQUEST, "Invalid discount amount"),
    COUPON_INVALID_MIN_ORDER_AMOUNT(HttpStatus.BAD_REQUEST, "Invalid minimum order amount"),
    COUPON_INVALID_DISCOUNT_RATE(HttpStatus.BAD_REQUEST, "Invalid discount rate"),
    COUPON_CATEGORY_NULL(HttpStatus.BAD_REQUEST, "Coupon category is null"),
    COUPON_VALID_FROM_NULL(HttpStatus.BAD_REQUEST, "Coupon valid from date is null"),
    COUPON_VALID_TO_NULL(HttpStatus.BAD_REQUEST, "Coupon valid to date is null"),
    COUPON_VALID_FROM_AFTER_VALID_TO(HttpStatus.BAD_REQUEST, "Valid from date is after valid to date");

    private final HttpStatus httpStatus;
    private final String message;
}
