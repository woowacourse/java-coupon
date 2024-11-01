package coupon.coupon.service;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(long couponId) {
        super("일치하는 쿠폰 정보가 없습니다. couponId : " + couponId);
    }
}
