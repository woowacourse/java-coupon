package coupon.domain;

public class DefaultMemberCouponPolicy implements MemberCouponPolicy {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final int memberCouponCount;

    public DefaultMemberCouponPolicy(int memberCouponCount) {
        this.memberCouponCount = memberCouponCount;
    }

    @Override
    public void validate() {
        if (memberCouponCount >= MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalStateException(String.format("한 회원에게 최대 %d장까지 발급할 수 있습니다.", MAX_MEMBER_COUPON_COUNT));
        }
    }
}
