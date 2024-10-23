package coupon.coupon.exception;

import coupon.advice.NotFoundException;

public class CouponNotFoundException extends NotFoundException {

    public CouponNotFoundException(long couponId) {
        super(String.format("%d에 해당하는 쿠폰을 찾을 수 없습니다.", couponId));
    }
}
