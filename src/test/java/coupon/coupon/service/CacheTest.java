package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
class CacheTest {

    @Autowired
    UserCouponService userCouponService;

    @Autowired
    CouponService couponService;

    @SpyBean
    CacheManager cacheManager;

    @SpyBean
    CouponRepository couponRepository;

    @Test
    void cachePut() {
        Coupon coupon = new Coupon("test", 1000, 10000, null, LocalDate.now(), LocalDate.now());
        Coupon savedCoupon = couponService.create(coupon);

        Cache cache = cacheManager.getCache("coupon");
        Cache.ValueWrapper valueWrapper = cache.get(savedCoupon.getId());

        Coupon cachedCoupon = (Coupon) valueWrapper.get();

        assertThat(cachedCoupon.getId()).isEqualTo(savedCoupon.getId());
    }

    @Test
    void cacheAble() {
        Coupon coupon = new Coupon("test", 1000, 10000, null, LocalDate.now(), LocalDate.now());
        Coupon savedCoupon = couponService.create(coupon);

        couponService.getCoupon(savedCoupon.getId());
        verify(couponRepository, never()).findById(savedCoupon.getId());
    }
}
