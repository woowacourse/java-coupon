package coupon.infrastructure;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponCacheRepository implements CouponRepository {

    private final RedisTemplate<String, Coupon> redisTemplate;

    @Override
    public Coupon save(Coupon coupon) {
        redisTemplate.opsForValue().set("coupon:" + coupon.getId(), coupon);
        return coupon;
    }

    @Override
    public Coupon findById(long id) {
        return redisTemplate.opsForValue().get("coupon:" + id);
    }
}
