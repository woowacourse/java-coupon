package coupon.exception;

public class CouponDiscountRateException extends CouponException {

    public CouponDiscountRateException(int discountRate) {
        super("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다. 입력된 할인율: " + discountRate);
    }
}
