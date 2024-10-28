package coupon.memberCoupon.repository;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.memberCoupon.domain.MemberCoupon;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class MemberCouponLocalCache {

    private static final Map<MemberCouponCacheKey, Set<MemberCoupon>> CACHE = new HashMap<>();

    public void put(Member member, Coupon coupon, MemberCoupon memberCoupon) {
        MemberCouponCacheKey memberCouponCacheKey = new MemberCouponCacheKey(member, coupon);

        if (CACHE.get(memberCouponCacheKey) == null) {
            Set<MemberCoupon> memberCouponCacheValue = new HashSet<>();
            memberCouponCacheValue.add(memberCoupon);

            CACHE.put(memberCouponCacheKey, memberCouponCacheValue);
            return;
        }
        CACHE.get(memberCouponCacheKey).add(memberCoupon);
    }

    public Set<MemberCoupon> get(Member member, Coupon coupon) {
        Set<MemberCoupon> memberCoupons = CACHE.get(new MemberCouponCacheKey(member, coupon));
        if (memberCoupons == null) {
            return Set.of();
        }
        return memberCoupons;
    }
}
