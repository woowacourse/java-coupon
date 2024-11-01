package coupon.api.repository;

import coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class CouponRedisRepository {

    private static final int ONE_DAY = 1;

    private final RedisTemplate<String, Coupon> couponRedisTemplate;

    @Transactional
    public void addCoupon(Coupon coupon) {
        couponRedisTemplate.opsForValue().set(
                String.valueOf(coupon.getId()),
                coupon, coupon.issueDayDeadLine() + ONE_DAY,
                TimeUnit.DAYS
        );
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> findCoupon(Long id) {
        return Optional.ofNullable(couponRedisTemplate.opsForValue().get(String.valueOf(id)));
    }
}
