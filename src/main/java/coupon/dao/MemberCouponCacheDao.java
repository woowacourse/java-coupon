package coupon.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponCacheDao {

    private static final String CACHE_KEY_MEMBER_COUPON_COUNT = "member-coupon:count";

    private final RedisTemplate<String, Long> redisTemplate;

    public Long incrementCount(long memberId, long couponId) {
        String key = generateKey(memberId, couponId);
        return redisTemplate.opsForValue().increment(key);
    }

    public boolean existKey(long memberId, long couponId) {
        String key = generateKey(memberId, couponId);
        return redisTemplate.opsForValue().get(key) != null;
    }

    public void setCount(long memberId, long couponId, long count) {
        String key = generateKey(memberId, couponId);
        redisTemplate.opsForValue().set(key, count);
    }

    private String generateKey(long memberId, long couponId) {
        return CACHE_KEY_MEMBER_COUPON_COUNT + ":" + memberId + ":" + couponId;
    }
}
