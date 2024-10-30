package coupon.support;

import coupon.domain.coupon.Coupon;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CouponCache {

    private final RedisTemplate<String, Coupon> redisTemplate;

    public Coupon get(Long couponId) {
        return redisTemplate.opsForValue().get(String.valueOf(couponId));
    }

    public void put(Long couponId, Coupon coupon) {
        redisTemplate.opsForValue().set(String.valueOf(couponId), coupon);
    }
}
