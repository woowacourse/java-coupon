package coupon.service.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CacheManager cacheManager;

    @DisplayName("복제 지연 테스트")
    @Test
    void replicaDelay() {
        Coupon coupon = createCoupon();
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("캐싱 테스트")
    void cachingTest() {
        Coupon coupon = createCoupon();
        couponRepository.save(coupon);

        couponService.getCoupon(coupon.getId());

        Coupon cachedCoupon = cacheManager.getCache("coupon").get(coupon.getId(), Coupon.class);
        assertThat(cachedCoupon.getId()).isEqualTo(coupon.getId());
    }

    private Coupon createCoupon() {
        return new Coupon(
                "쿠폰",
                1000,
                10000,
                CouponCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
    }
}

