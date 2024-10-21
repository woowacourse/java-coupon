package coupon.coupon.exception;

public enum CouponErrorMessage {

    CANNOT_FIND_COUPON("쿠폰을 조회할 수 없습니다.");

    private final String message;

    CouponErrorMessage(String message) {
        this.message = message;

    }
}
