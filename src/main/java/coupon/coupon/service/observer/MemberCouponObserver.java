package coupon.coupon.service.observer;

public interface MemberCouponObserver {

    void onIssue(Long memberCouponId);

    void onUse(Long memberCouponId);
}
