package coupon.exception;

public class CouponNameException extends CouponException {

    public CouponNameException() {
        super("쿠폰 이름은 반드시 존재해야 하고, 30자 이상이어야 합니다.");
    }
}
