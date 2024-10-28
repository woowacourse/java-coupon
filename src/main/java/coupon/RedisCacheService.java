package coupon;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisCacheService {

    private static final String COUPON_CACHE_KEY_PREFIX = "coupon:";
    private static final String MEMBER_COUPON_CACHE_KEY_PREFIX = "memberCoupon:";
    private static final int INITIAL_TIME_TO_LIVE_SECONDS = 10;
    private static final int EXTEND_TIME_TO_LIVE_SECONDS = 60;

    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<Coupon> getCouponFromCache(Long id) {
        log.info("Coupon with ID {} found in cache.", id);
        String redisKey = COUPON_CACHE_KEY_PREFIX + id;
        Coupon coupon = (Coupon) redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(coupon);
    }

    public Optional<MemberCoupon> getMemberCouponFromCache(Long id) {
        log.info("Coupon with ID {} found in cache.", id);
        String redisKey = MEMBER_COUPON_CACHE_KEY_PREFIX + id;
        MemberCoupon memberCoupon = (MemberCoupon) redisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(memberCoupon);
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

    public void cache(MemberCoupon memberCoupon) {
        Long id = memberCoupon.getId();
        if (isNotInTransaction()) {
            log.info("MemberCoupon with ID {} doesn't cache without transaction.", id);
            return;
        }
        if (isInReadOnlyTransaction()) {
            log.info("MemberCoupon with ID {} cache in read-only transaction.", id);
            cacheCoupon(memberCoupon);
            return;
        }
        log.info("MemberCoupon with ID {} cache in transaction.", id);
        TransactionSynchronizationManager.registerSynchronization(runAfterCommit(() -> cacheCoupon(memberCoupon)));
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

    private void cacheCoupon(MemberCoupon memberCoupon) {
        String redisKey = MEMBER_COUPON_CACHE_KEY_PREFIX + memberCoupon.getId();
        cacheWithTTL(redisKey, memberCoupon, INITIAL_TIME_TO_LIVE_SECONDS);
        log.info("MemberCoupon with ID {} cached in Redis.", memberCoupon.getId());
    }

    private void cacheWithTTL(String redisKey, Coupon coupon, int timeToLiveSeconds) {
        redisTemplate.opsForValue().set(redisKey, coupon, timeToLiveSeconds, TimeUnit.SECONDS);
    }

    private void cacheWithTTL(String redisKey, MemberCoupon MemberCoupon, int timeToLiveSeconds) {
        redisTemplate.opsForValue().set(redisKey, MemberCoupon, timeToLiveSeconds, TimeUnit.SECONDS);
    }

    @Async
    public CompletableFuture<Coupon> extendCacheTTL(Coupon coupon) {
        Long id = coupon.getId();
        String redisKey = COUPON_CACHE_KEY_PREFIX + id;
        Long ttl = Objects.requireNonNull(redisTemplate.getExpire(redisKey, TimeUnit.SECONDS));
        if (INITIAL_TIME_TO_LIVE_SECONDS < ttl) {
            log.info("Coupon with ID {} TTL remains {} seconds", id, ttl);
            return CompletableFuture.completedFuture(coupon);
        }
        cacheWithTTL(redisKey, coupon, EXTEND_TIME_TO_LIVE_SECONDS);
        log.info("Coupon with ID {} extends TTL", id);
        return CompletableFuture.completedFuture(coupon);
    }

    @Async
    public CompletableFuture<MemberCoupon> extendCacheTTL(MemberCoupon memberCoupon) {
        Long id = memberCoupon.getId();
        String redisKey = MEMBER_COUPON_CACHE_KEY_PREFIX + id;
        Long ttl = Objects.requireNonNull(redisTemplate.getExpire(redisKey, TimeUnit.SECONDS));
        if (INITIAL_TIME_TO_LIVE_SECONDS < ttl) {
            log.info("Coupon with ID {} TTL remains {} seconds", id, ttl);
            return CompletableFuture.completedFuture(memberCoupon);
        }
        cacheWithTTL(redisKey, memberCoupon, EXTEND_TIME_TO_LIVE_SECONDS);
        log.info("Coupon with ID {} extends TTL", id);
        return CompletableFuture.completedFuture(memberCoupon);
    }
}
