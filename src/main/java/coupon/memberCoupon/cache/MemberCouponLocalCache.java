package coupon.memberCoupon.cache;

import coupon.memberCoupon.domain.MemberCoupon;
import coupon.memberCoupon.dto.MemberCouponResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MemberCouponLocalCache {

    private static final Map<MemberCouponCacheKey, Set<MemberCoupon>> CACHE = new HashMap<>();

    public void put(Long memberId, Long couponId, MemberCoupon memberCoupon) {
        MemberCouponCacheKey memberCouponCacheKey = new MemberCouponCacheKey(memberId, couponId);

        if (!CACHE.containsKey(memberCouponCacheKey)) {
            Set<MemberCoupon> memberCouponCacheValue = new HashSet<>();
            memberCouponCacheValue.add(memberCoupon);

            CACHE.put(memberCouponCacheKey, memberCouponCacheValue);
            return;
        }
        CACHE.get(memberCouponCacheKey).add(memberCoupon);
    }

    public Set<MemberCoupon> get(Long memberId, Long couponId) {
        return CACHE.getOrDefault(new MemberCouponCacheKey(memberId, couponId), Set.of());
    }

    public Set<MemberCouponResponse> getAllByMemberId(Long memberId) {
        return CACHE.entrySet().stream()
                .filter(entry -> entry.getKey().getMemberId().equals(memberId))
                .flatMap(entry -> entry.getValue().stream()
                        .map(memberCoupon -> MemberCouponResponse.create(entry.getKey().getCouponId(), memberCoupon)))
                .collect(Collectors.toSet());
    }
}
