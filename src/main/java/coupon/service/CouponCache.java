package coupon.service;

import coupon.repository.CouponEntity;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class CouponCache {

    private static final String COUPON_KEY_NAME = "COUPON";

    private final RedisTemplate<String, CouponEntity> redisTemplate;

    public Optional<CouponEntity> findById(Long id) {
        try {
            CouponEntity couponEntity = redisTemplate.opsForValue().get(COUPON_KEY_NAME + id);
            return Optional.ofNullable(couponEntity);
        } catch (Exception e) {
            log.info("cache read fail");
            return Optional.empty();
        }
    }

    public void save(CouponEntity couponEntity) {
        long ttlNanoSeconds = calculateTTL(couponEntity);
        try {
            redisTemplate.opsForValue()
                    .set(COUPON_KEY_NAME + couponEntity.getId(), couponEntity, ttlNanoSeconds, TimeUnit.NANOSECONDS);
            log.info("save cache success");
        } catch (Exception e) {
            log.info("save cache fail");
        }
    }

    private long calculateTTL(CouponEntity couponEntity) {
        LocalDateTime end = couponEntity.getEnd();
        Duration duration = Duration.between(LocalDateTime.now(), end);
        long seconds = duration.getSeconds();
        long nanoSeconds = duration.getNano();
        return convertToNanoSeconds(seconds) + nanoSeconds;
    }

    private long convertToNanoSeconds(long seconds) {
        return seconds * 1_000_000_000L;
    }
}
