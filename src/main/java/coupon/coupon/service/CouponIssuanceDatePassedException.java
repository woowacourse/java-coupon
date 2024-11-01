package coupon.coupon.service;

public class CouponIssuanceDatePassedException extends RuntimeException {
    public CouponIssuanceDatePassedException(long couponId) {
        super("쿠폰의 발급 가능일이 지났습니다. couponId : " + couponId);
    }
}
