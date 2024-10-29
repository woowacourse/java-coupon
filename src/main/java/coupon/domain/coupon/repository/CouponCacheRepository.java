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

    private static final String COUPON_ISSUE_COUNT_UP_SCRIPT =
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
                    "end";

    private final RedisTemplate redisTemplate;

    public Coupon getCoupon(long couponId) {
        String couponKey = RedisKey.COUPON.getKey(couponId);
        return (Coupon) redisTemplate.opsForValue().get(couponKey);
    }

    public void updateIssuedMemberCouponCount(long memberId, long couponId) {
        String key = RedisKey.MEMBER_COUPONS.getKey(memberId, couponId);
        DefaultRedisScript<Object> script = new DefaultRedisScript<>();
        script.setScriptText(COUPON_ISSUE_COUNT_UP_SCRIPT);
        script.setResultType(Object.class);

        int result = (Integer) redisTemplate.execute(script, Collections.singletonList(key));
        if(result == -1) {
            throw new MemberCouponIssueLimitException();
        }
    }
}
