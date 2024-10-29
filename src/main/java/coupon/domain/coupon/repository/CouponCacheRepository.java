package coupon.domain.coupon.repository;

import coupon.config.cache.RedisKey;
import coupon.domain.coupon.Coupon;
import coupon.exception.MemberCouponIssueLimitException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCacheRepository {
    private static final DefaultRedisScript<Object> COUPON_ISSUE_COUNT_UP_SCRIPT = new DefaultRedisScript<>(
            "local value = redis.call('GET', KEYS[1]); " +
                    "if not value then " +
                    "    value = 0; " +
                    "    redis.call('SET', KEYS[1], value); " +
                    "end; " +
                    "if tonumber(value) >= 5 then " +
                    "    return '-1'; " +
                    "else " +
                    "    value = tonumber(value) + 1; " +
                    "    redis.call('SET', KEYS[1], value); " +
                    "    return tostring(value); " +
                    "end",
            Object.class
    );

    private final RedisTemplate redisTemplate;

    public Coupon getCoupon(long couponId) {
        String couponKey = RedisKey.COUPON.getKey(couponId);
        return (Coupon) redisTemplate.opsForValue().get(couponKey);
    }

    public void updateIssuedMemberCouponCount(long memberId, long couponId) {
        String key = RedisKey.MEMBER_COUPONS.getKey(memberId, couponId);

        int result = (Integer) redisTemplate.execute(COUPON_ISSUE_COUNT_UP_SCRIPT, Collections.singletonList(key));
        if (result == -1) {
            throw new MemberCouponIssueLimitException();
        }
    }
}
