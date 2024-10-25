package coupon;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisCacheService {

    private static final String COUPON_CACHE_KEY_PREFIX = "coupon:";
    private static final int INITIAL_TIME_TO_LIVE_SECONDS = 10;
    private static final int EXTEND_TIME_TO_LIVE_SECONDS = 60;

    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<Coupon> getCouponFromCache(Long id) {
        log.info("Coupon with ID {} found in cache.", id);
        String redisKey = COUPON_CACHE_KEY_PREFIX + id;
        Coupon coupon = (Coupon) redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(coupon);
    }

    public void cache(Coupon coupon) {
        Long id = coupon.getId();
        if (isNotInTransaction()) {
            log.info("Coupon with ID {} doesn't cache without transaction.", id);
            return;
        }
        if (isInReadOnlyTransaction()) {
            log.info("Coupon with ID {} cache in read-only transaction.", id);
            cacheCoupon(coupon);
            return;
        }
        log.info("Coupon with ID {} cache in transaction.", id);
        TransactionSynchronizationManager.registerSynchronization(runAfterCommit(() -> cacheCoupon(coupon)));
    }

    private boolean isNotInTransaction() {
        return !TransactionSynchronizationManager.isSynchronizationActive();
    }

    private boolean isInReadOnlyTransaction() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly();
    }

    private TransactionSynchronization runAfterCommit(Runnable runnable) {
        return new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                runnable.run();
            }
        };
    }

    private void cacheCoupon(Coupon coupon) {
        String redisKey = COUPON_CACHE_KEY_PREFIX + coupon.getId();
        cacheWithTTL(redisKey, coupon, INITIAL_TIME_TO_LIVE_SECONDS);
        log.info("Coupon with ID {} cached in Redis.", coupon.getId());
    }

    private void cacheWithTTL(String redisKey, Coupon coupon, int timeToLiveSeconds) {
        redisTemplate.opsForValue().set(redisKey, coupon, timeToLiveSeconds, TimeUnit.SECONDS);
    }

    public void extendCacheTTL(Coupon coupon) {
        Long id = coupon.getId();
        String redisKey = COUPON_CACHE_KEY_PREFIX + id;
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        if (10L < ttl) {
            log.info("Coupon with ID {} not extends TTL", id);
            return;
        }
        cacheWithTTL(redisKey, coupon, EXTEND_TIME_TO_LIVE_SECONDS);
        log.info("Coupon with ID {} extends TTL", id);
    }
}
