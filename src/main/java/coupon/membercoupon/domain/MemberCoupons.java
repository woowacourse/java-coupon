package coupon.membercoupon.domain;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCoupons {

    private final List<MemberCoupon> memberCoupons;

    public MemberCoupons(MemberCoupon... memberCoupons) {
        this.memberCoupons = List.of(memberCoupons);
    }

    public List<Long> getCouponIds() {
        return memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();
    }

    public int getSize() {
        return getCouponIds().size();
    }

    public MemberCoupon getMemberCouponByOrder(int order) {
        return memberCoupons.get(order);
    }
}
