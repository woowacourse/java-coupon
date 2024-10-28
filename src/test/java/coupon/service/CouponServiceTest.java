package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.fixture.CouponFixture;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @SpyBean
    private CouponRepository couponRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void tearDown() {
        couponRepository.deleteAllInBatch();
        Optional.ofNullable(redisTemplate.keys("*")).ifPresent(redisTemplate::delete);
    }

    @DisplayName("복제 지연 테스트")
    @Test
    void replicaDelay() {
        Coupon coupon = CouponFixture.TEST_COUPON;
        long id = couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(id);
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("캐싱 테스트")
    void cachingTest() {
        // given
        Coupon coupon = CouponFixture.TEST_COUPON;
        couponRepository.save(coupon);

        // when
        couponService.getCoupon(coupon.getId());

        // then
        Coupon cachedCoupon = cacheManager.getCache("coupon").get(coupon.getId(), Coupon.class);
        assertThat(cachedCoupon.getId()).isEqualTo(coupon.getId());
    }
}
