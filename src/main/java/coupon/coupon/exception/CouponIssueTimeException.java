package coupon.coupon.exception;

public class CouponIssueTimeException extends RuntimeException {

    private static final String MESSAGE = "쿠폰을 발급할 수 없는 시간입니다.";

    public CouponIssueTimeException() {
        super(MESSAGE);
    }
}

