package coupon.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorConstant {

    COUPON_NAME_IS_NULL_OR_EMPTY(HttpStatus.BAD_REQUEST, "쿠폰의 이름은 비어있을 수 없습니다."),
    COUPON_NAME_IS_NOT_IN_RANGE(HttpStatus.BAD_REQUEST, "너무 긴 쿠폰 이름입니다."),
    DISCOUNT_PRICE_NOT_IN_RANGE(HttpStatus.BAD_REQUEST, "가능한 할인 금액이 아닙니다."),
    COUPON_PRICE_RATE_NOT_IN_RANGE(HttpStatus.BAD_REQUEST, "쿠폰의 할인율이 너무 높거나 낮습니다."),
    NOT_AVAILABLE_UNIT_PRICE(HttpStatus.BAD_REQUEST, "가능한 금액 단위가 아닙니다."),
    COUPON_ISSUE_DATE_IS_NULL(HttpStatus.BAD_REQUEST, "쿠폰의 날짜는 비어있을 수 없습니다."),
    NOT_AVAILABLE_COUPON_DATE(HttpStatus.BAD_REQUEST, "쿠폰의 시작일이 끝나는 날보다 늦을 수 없습니다."),
    MEMBER_NAME_IS_NULL_OR_BLANK(HttpStatus.BAD_REQUEST, "이름은 비어있을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
