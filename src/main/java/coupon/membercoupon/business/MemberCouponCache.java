package coupon.membercoupon.business;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import coupon.membercoupon.domain.MemberCoupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MemberCouponCache {

    private static final int MAXIMUM_ISSUABLE_MEMBER_COUPON = 5;

    private static final Map<Long, List<MemberCoupon>> CACHED_MEMBER_COUPONS = new HashMap<>();

    public static void add(Long memberId, MemberCoupon memberCoupon) {
        List<MemberCoupon> memberCoupons = CACHED_MEMBER_COUPONS.getOrDefault(memberId, new ArrayList<>());
        if(memberCoupons.size() >= MAXIMUM_ISSUABLE_MEMBER_COUPON) {
            throw new CouponException(CouponErrorMessage.EXCEED_MAXIMUM_ISSUABLE_COUPON);
        }
        memberCoupons.add(memberCoupon);
        CACHED_MEMBER_COUPONS.put(memberId, memberCoupons);
    }

    public static List<MemberCoupon> get(Long memberId) {
        return CACHED_MEMBER_COUPONS.get(memberId);
    }
}
