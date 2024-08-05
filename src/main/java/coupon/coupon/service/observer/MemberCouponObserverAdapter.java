package coupon.coupon.service.observer;

/**
 * {@link MemberCouponObserver}의 메서드 중 일부 이벤트만 처리하고 싶을 때 사용하는 어댑터.
 */
public class MemberCouponObserverAdapter implements MemberCouponObserver {
    @Override
    public void onIssue(Long memberCouponId) {
        // Do nothing.
    }

    @Override
    public void onUse(Long memberCouponId) {
        // Do nothing.
    }
}
