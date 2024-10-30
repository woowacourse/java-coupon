package coupon.api.repository;

import coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRedisRepository {

    private final RedisTemplate<String, Coupon> couponRedisTemplate;

    @Transactional
    public void addCoupon(Coupon coupon) {
        couponRedisTemplate.opsForValue().set(String.valueOf(coupon.getId()), coupon);
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> findCoupon(Long id) {
        return Optional.ofNullable(couponRedisTemplate.opsForValue().get(String.valueOf(id)));
    }
}
