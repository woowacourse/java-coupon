package coupon.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionType {

    COUPON_NAME_BLANK(HttpStatus.BAD_REQUEST, "쿠폰 이름을 입력해주세요."),
    COUPON_NAME_LENGTH_EXCEED(HttpStatus.BAD_REQUEST, "쿠폰 이름은 30자 이하여야합니다."),
    COUPON_DISCOUNT_AMOUNT_INVALID(HttpStatus.BAD_REQUEST, "할인 금액은 1,000원 이상 10,000원 이하여야합니다."),
    COUPON_DISCOUNT_UNIT_MISMATCH(HttpStatus.BAD_REQUEST, "할인 금액은 500원 단위여야 합니다."),
    COUPON_MINIMUM_ORDER_AMOUNT_INVALID(HttpStatus.BAD_REQUEST, "최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");

    private final HttpStatus status;
    private final String message;
}
