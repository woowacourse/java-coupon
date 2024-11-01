package coupon.membercoupon.service;

public class MemberCouponIssuedOverException extends RuntimeException {
    public MemberCouponIssuedOverException(long memberId) {
        super("쿠폰은 최대 5장까지 발급할 수 있습니다. memberId : " + memberId);
    }
}
