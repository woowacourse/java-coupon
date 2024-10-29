package coupon.infrastructure;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponCacheRepository {

    private final RedisTemplate<String, Coupon> redisTemplate;

    public void save(Coupon coupon) {
        redisTemplate.opsForValue().set("coupon:" + coupon.getId(), coupon);
    }

    public Coupon getCachedCoupon(long id) {
        return redisTemplate.opsForValue().get("coupon:" + id);
    }
}
