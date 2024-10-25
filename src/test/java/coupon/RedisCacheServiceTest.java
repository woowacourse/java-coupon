package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import coupon.cleaner.DatabaseCleanerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@ExtendWith(DatabaseCleanerExtension.class)
@SpringBootTest
class RedisCacheServiceTest {

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private static final String COUPON_CACHE_KEY_PREFIX = "coupon:";
    private static final long INITIAL_TIME_TO_LIVE_SECONDS = 10;
    private static final long EXTEND_TIME_TO_LIVE_SECONDS = 60;

    private Coupon testCoupon;

    @BeforeEach
    void setUp() {
        testCoupon = new Coupon("Test Coupon", 1000, Category.FASHION, 5000);
    }

    @DisplayName("캐싱된 쿠폰이 존재하면 가져온다.")
    @Test
    void getCouponFromCache() {
        String redisKey = COUPON_CACHE_KEY_PREFIX + testCoupon.getId();
        redisTemplate.opsForValue().set(redisKey, testCoupon, 1, TimeUnit.SECONDS);

        Optional<Coupon> couponFromCache = redisCacheService.getCouponFromCache(testCoupon.getId());

        assertThat(couponFromCache).isPresent();
        assertThat(couponFromCache.get().getId()).isEqualTo(testCoupon.getId());
    }

    @DisplayName("캐싱된 쿠폰이 존재하지 않으면 빈 Optional을 반환한다.")
    @Test
    void getCouponFromCacheOptionalEmpty() {
        Optional<Coupon> couponFromCache = redisCacheService.getCouponFromCache(testCoupon.getId());

        assertThat(couponFromCache.isEmpty()).isTrue();
    }

    @DisplayName("트랜잭션을 수동으로 관리하여 캐시 저장을 테스트")
    @Test
    void cacheCouponWithManualTransactionManagement() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            redisCacheService.cache(testCoupon);
        });

        String redisKey = COUPON_CACHE_KEY_PREFIX + testCoupon.getId();
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        Optional<Coupon> couponFromCache = redisCacheService.getCouponFromCache(testCoupon.getId());

        assertThat(ttl).isBetween(INITIAL_TIME_TO_LIVE_SECONDS - 1, INITIAL_TIME_TO_LIVE_SECONDS);
        assertThat(couponFromCache).isNotEmpty();
    }

    @DisplayName("트랜잭션이 없으면 캐싱하지 않는다.")
    @Test
    void cacheCouponWithoutTransaction() {
        redisCacheService.cache(testCoupon);

        Optional<Coupon> couponFromCache = redisCacheService.getCouponFromCache(testCoupon.getId());

        assertThat(couponFromCache.isEmpty()).isTrue();
    }

    @DisplayName("캐시에 저장되어 있는 쿠폰을 조회하여 캐시 히트할 경우 TTL을 60초로 연장한다.")
    @Test
    void extendCacheTTL() {
        redisCacheService.extendCacheTTL(testCoupon);

        String redisKey = COUPON_CACHE_KEY_PREFIX + testCoupon.getId();
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        Optional<Coupon> couponFromCache = redisCacheService.getCouponFromCache(testCoupon.getId());

        assertThat(ttl).isBetween(EXTEND_TIME_TO_LIVE_SECONDS - 1, EXTEND_TIME_TO_LIVE_SECONDS);
        assertThat(couponFromCache).isNotEmpty();
    }

    @DisplayName("쿠폰의 잔여 TTL이 10초 초과면, 캐시 메모리에서 조회되어도 다시 연장하지 않는다.")
    @Test
    void doNotExtendCacheTTLWhenCouponTtlExceeded10Seconds() {
        // 10초 초과인 TTL 쿠폰을 캐싱해두고, 캐시 히트되어 CouponService에서 캐싱 요청시
        // 연장하지 않고 그대로 메서드를 종료한다.
        String redisKey = COUPON_CACHE_KEY_PREFIX + testCoupon.getId();

        redisCacheService.extendCacheTTL(testCoupon);
        Long firstTtl = redisTemplate.getExpire(redisKey, TimeUnit.MICROSECONDS);

        redisCacheService.extendCacheTTL(testCoupon);
        Long secondTtl = redisTemplate.getExpire(redisKey, TimeUnit.MICROSECONDS);

        assertThat(firstTtl).isGreaterThan(secondTtl);
    }
}
