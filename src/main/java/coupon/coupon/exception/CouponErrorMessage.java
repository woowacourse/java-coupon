package coupon.coupon.exception;

public enum CouponErrorMessage {

    CANNOT_FIND_COUPON("쿠폰을 조회할 수 없습니다."),
    INVALID_DISCOUNT_PRICE("올바르지 않은 쿠폰 할인 금액입니다."),
    INVALID_DISCOUNT_PERCENT("올바르지 않은 쿠폰 할인율입니다."),
    INVALID_MINIMUM_ORDER_AMOUNT("올바르지 않은 최소 주문 금액입니다.");

    private final String message;

    CouponErrorMessage(String message) {
        this.message = message;

    }
}
