package coupon.coupon.exception;

public class CouponIssueLimitExceededException extends RuntimeException {

    private static final String MESSAGE = "쿠폰을 더 이상 발급할 수 없습니다.";

    public CouponIssueLimitExceededException() {
        super(MESSAGE);
    }
}
