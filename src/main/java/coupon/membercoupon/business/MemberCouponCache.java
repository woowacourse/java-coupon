package coupon.membercoupon.business;

import coupon.membercoupon.domain.MemberCoupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MemberCouponCache {

    private static final Map<Long, List<MemberCoupon>> CACHED_MEMBER_COUPONS = new HashMap<>();

    private MemberCouponCache() {
    }

    public static void add(Long memberId, MemberCoupon memberCoupon) {
        List<MemberCoupon> memberCoupons = CACHED_MEMBER_COUPONS.getOrDefault(memberId, new ArrayList<>());
        memberCoupons.add(memberCoupon);
        CACHED_MEMBER_COUPONS.put(memberId, memberCoupons);
    }

    public static List<MemberCoupon> get(Long memberId) {
        return CACHED_MEMBER_COUPONS.get(memberId);
    }
}
