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
    COUPON_MINIMUM_ORDER_AMOUNT_INVALID(HttpStatus.BAD_REQUEST, "최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다."),
    COUPON_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 쿠폰 카테고리입니다."),
    COUPON_DATE_INVALID(HttpStatus.BAD_REQUEST, "쿠폰 시작일은 종료일보다 이전이거나 같아야 합니다."),
    COUPON_DISCOUNT_RATE(HttpStatus.BAD_REQUEST, "쿠폰 할인율은 3% 이상 20% 이하여야 합니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 쿠폰입니다.");

    private final HttpStatus status;
    private final String message;
}
