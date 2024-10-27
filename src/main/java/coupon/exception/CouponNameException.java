package coupon.exception;

public class CouponNameException extends CouponException {

    public CouponNameException(String name) {
        super("쿠폰 이름은 반드시 존재해야 하고, 30자 이상이어야 합니다. 입력된 이름: " + name);
    }
}
