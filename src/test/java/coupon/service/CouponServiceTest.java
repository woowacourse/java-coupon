package coupon.service;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import coupon.support.Fixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
class CouponServiceTest {

    private static final String COUPON_CACHE_NAME = "coupon";

    @Autowired
    private CouponService couponService;

    @SpyBean
    private CacheManager cacheManager;

    @SpyBean
    private CouponRepository couponRepository;

    @Test
    void 복제지연테스트() {
        Coupon saved = couponService.save(Fixture.createCoupon());

        assertThatCode(() -> couponService.findById(saved.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 쿠폰_발행_시_캐시에_저장한다() {
        Coupon dbCoupon = couponService.save(Fixture.createCoupon());
        Cache couponCache = requireNonNull(cacheManager.getCache(COUPON_CACHE_NAME));

        assertThat(couponCache.get(dbCoupon.getId(), Coupon.class)) // 캐시 조회
                .extracting(Coupon::getId)
                .isEqualTo(dbCoupon.getId());
    }

    @Test
    void 쿠폰_조회_시_캐시를_사용한다() {
        Coupon dbCoupon = couponService.save(Fixture.createCoupon());

        Cache spyCache = spy(requireNonNull(cacheManager.getCache(COUPON_CACHE_NAME)));
        when(cacheManager.getCache("coupon")).thenReturn(spyCache);

        Coupon coupon = couponService.findById(dbCoupon.getId());

        assertAll(
                () -> assertThat(coupon.getId()).isEqualTo(dbCoupon.getId()),
                () -> verify(spyCache, times(1)).get(dbCoupon.getId()),
                () -> verify(couponRepository, never()).findById(dbCoupon.getId())
        );
    }
}
