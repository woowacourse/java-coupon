package coupon.coupon.exception;

import coupon.coupon.domain.Coupon;

public enum CouponErrorMessage {

    CANNOT_FIND_COUPON("쿠폰을 조회할 수 없습니다."),
    INVALID_DISCOUNT_PRICE("올바르지 않은 쿠폰 할인 금액입니다."),
    INVALID_DISCOUNT_PERCENT("올바르지 않은 쿠폰 할인율입니다."),
    INVALID_MINIMUM_ORDER_AMOUNT("올바르지 않은 최소 주문 금액입니다."),
    NAME_TOO_LONG(String.format("쿠폰 이름을 %d자 이하로 설정해주세요.", Coupon.MAX_NAME_LENGTH)),
    NAME_CANNOT_EMPTY("쿠폰 이름을 입력해주세요."),
    START_CANNOT_BE_AFTER_END("시작일은 종료일보다 이후일 수 없습니다."),
    EXCEED_MAXIMUM_ISSUABLE_COUPON("최대 발급 가능한 쿠폰 개수를 초과하였습니다."),
    COUPON_DOES_NOT_EXISTS_IN_MEMBER_COUPON("회원 쿠폰에 등록된 쿠폰이 존재하지 않습니다.");

    private final String message;

    CouponErrorMessage(String message) {
        this.message = message;
    }
}
