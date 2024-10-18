package coupon.exception;

public class CouponMinimumOrderAmountException extends CouponException {

    public CouponMinimumOrderAmountException() {
        super("쿠폰 사용을 위한 최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }
}
