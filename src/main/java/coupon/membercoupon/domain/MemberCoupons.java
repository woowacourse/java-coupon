package coupon.membercoupon.domain;

import java.util.List;

public class MemberCoupons {
    private static final long MAXIMUM_SAME_MEMBER_COUPON_COUNT = 5;

    private final List<MemberCoupon> memberCoupons;

    public MemberCoupons(List<MemberCoupon> memberCoupons) {
        this.memberCoupons = memberCoupons;
    }

    public boolean hasSizeFiveOrMore() {
        return memberCoupons.size() >= MAXIMUM_SAME_MEMBER_COUPON_COUNT;
    }

    public List<Long> getCouponIds() {
        return memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();
    }

    public List<MemberCoupon> getMemberCoupons() {
        return memberCoupons;
    }
}
