package coupon.repository.cache;

import coupon.domain.Coupon;
import coupon.entity.cache.CachedCouponEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class CachedCouponRepositoryTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    CachedCouponRepository cachedCouponRepository;

    @AfterEach
    void tearDown() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @DisplayName("쿠폰을 캐싱한다.")
    @Test
    void saveCachedCoupon() {
        Coupon coupon = new Coupon(1L, 1000, 10000);
        CachedCouponEntity cachedCouponEntity = cachedCouponRepository.save(new CachedCouponEntity(coupon));

        Optional<CachedCouponEntity> optionalCachedCoupon = cachedCouponRepository.findById(cachedCouponEntity.getId());

        assertAll(
                () -> Assertions.assertThat(optionalCachedCoupon).isNotNull(),
                () -> Assertions.assertThat(optionalCachedCoupon.get().getId()).isEqualTo(1L)
        );
    }
}
