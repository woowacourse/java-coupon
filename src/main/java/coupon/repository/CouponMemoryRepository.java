package coupon.repository;

import coupon.domain.Coupon;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponMemoryRepository {

    private static final String COUPON_KEY_PREFIX = "coupon_";

    private final RedisTemplate<String, Coupon> redisTemplate;

    public void save(Coupon coupon) {
        redisTemplate.opsForValue().set(key(coupon), coupon);
    }

    public Optional<Coupon> findById(Long id) {
        Coupon coupon = redisTemplate.opsForValue().get(key(id));
        return Optional.ofNullable(coupon);
    }

    private String key(Coupon coupon) {
        return COUPON_KEY_PREFIX + coupon.getId();
    }

    private String key(Long couponId) {
        return COUPON_KEY_PREFIX + couponId;
    }
}
