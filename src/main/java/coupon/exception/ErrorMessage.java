package coupon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    INVALID_COUPON_NAME("쿠폰 이름은 30자 이하입니다.", HttpStatus.BAD_REQUEST),
    INVALID_COUPON_DISCOUNT_AMOUNT_UNIT("쿠폰 할인금액은 500원 단위입니다.", HttpStatus.BAD_REQUEST),
    INVALID_COUPON_DISCOUNT_AMOUNT_RANGE("쿠폰 할인금액은 1,000원 이상 10,000원 이하입니다.", HttpStatus.BAD_REQUEST),
    INVALID_MINIMUM_ORDER_PRICE_RANGE("쿠폰의 최소 주문 금액은 5,000원 이상 100,000원 이하입니다.", HttpStatus.BAD_REQUEST),
    INVALID_DISCOUNT_RATE_RANGE("쿠폰의 할인율은 3% 이상 20% 이하입니다.", HttpStatus.BAD_REQUEST),
    INVALID_ISSUE_PERIOD("쿠폰 발급 시작일은 종료일 이전이어야 합니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
