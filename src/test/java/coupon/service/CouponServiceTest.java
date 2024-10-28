package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.CouponPeriod;
import coupon.domain.DiscountAmount;
import coupon.domain.MinimumOrderAmount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class CouponServiceTest {

    @MockBean
    private CouponReadService couponReadService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void clearCache() {
        cacheManager.getCache("coupons").clear();
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(new CouponName("zeze"), new DiscountAmount(1000, 5000),
                new MinimumOrderAmount(5000), Category.FASHION, new CouponPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        couponService.createCoupon(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        Assertions.assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("쿠폰 조회시 캐시를 사용한다.")
    @Test
    void cache() {
        // given
        Coupon coupon = new Coupon(new CouponName("zeze-coupon"), new DiscountAmount(1000, 5000),
                new MinimumOrderAmount(5000), Category.FASHION, new CouponPeriod(
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        Coupon savedCoupon = couponService.createCoupon(coupon);

        when(couponReadService.getCoupon(savedCoupon.getId())).thenReturn(Optional.of(savedCoupon));

        // when
        for (int i = 0; i <= 10; i++) {
            couponService.getCoupon(savedCoupon.getId());
        }

        // then
        verify(couponReadService, times(1)).getCoupon(savedCoupon.getId());
    }
}
