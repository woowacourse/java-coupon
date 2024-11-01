package coupon.redis;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.fixture.CouponFixture;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Coupon> redisTemplate;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 쿠폰_캐시_테스트() {
        Coupon coupon = couponRepository.save(CouponFixture.create());

        redisTemplate.opsForValue().set("coupon:" + coupon.getId(), coupon);

        Coupon cachedCoupon = redisTemplate.opsForValue().get("coupon:" + coupon.getId());
        assertThat(cachedCoupon).isNotNull();
        assertThat(cachedCoupon.getDiscountRate()).isEqualTo(10);
    }
}
