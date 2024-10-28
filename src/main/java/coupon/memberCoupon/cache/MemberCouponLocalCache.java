package coupon.memberCoupon.cache;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.memberCoupon.domain.MemberCoupon;
import coupon.memberCoupon.dto.MemberCouponResponse;
import coupon.memberCoupon.repository.MemberCouponCacheKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
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

    public Set<MemberCouponResponse> getAllByMember(Member member) {
        return CACHE.entrySet().stream()
                .filter(entry -> entry.getKey().getMember().equals(member))
                .flatMap(entry -> entry.getValue().stream()
                        .map(memberCoupon -> MemberCouponResponse.create(entry.getKey().getCoupon(), memberCoupon)))
                .collect(Collectors.toSet());
    }
}
