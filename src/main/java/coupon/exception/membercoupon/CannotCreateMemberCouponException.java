package coupon.exception.membercoupon;

import coupon.exception.coupon.CouponException;

public class CannotCreateMemberCouponException extends CouponException {

    public CannotCreateMemberCouponException() {
        super("한 명의 회원에게 동일한 쿠폰은 사용한 쿠폰을 포함해 최대 5장까지만 발급 가능합니다.");
    }
}
