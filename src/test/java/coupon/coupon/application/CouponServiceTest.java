package coupon.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.fixture.CouponFixture;

@SpringBootTest
@Transactional
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @Test
    void 복제지연테스트() {
        Coupon savedCoupon = couponService.create(CouponFixture.createCoupon());
        Coupon coupon = couponService.getCoupon(savedCoupon.getId());
        assertThat(coupon).isNotNull();
    }

    @Nested
    @DisplayName("쿠폰 서비스: 캐시 관련 로직 테스트")
    class CacheCouponTest {

        @SpyBean
        private CouponRepository couponRepository;

        @BeforeEach
        void setUp() {
            cache = cacheManager.getCache("coupon");
            cache.clear();
        }

        @Test
        @DisplayName("create 메소드 호출 시 캐시 갱신")
        void createShouldUpdateCache() {
            Coupon savedCoupon = couponService.create(CouponFixture.createCoupon());
            Coupon cachedCoupon = cache.get(savedCoupon.getId(), Coupon.class);

            assertThat(cachedCoupon).isNotNull();
            assertThat(cachedCoupon.getId()).isEqualTo(savedCoupon.getId());
        }

        @Test
        @DisplayName("쿠폰 조회: 캐시 적용 확인")
        void getCouponShouldUseCachedData() {
            Coupon coupon = CouponFixture.createCouponWithId();

            Coupon savedCoupon = couponService.create(coupon);
            Coupon cachedCoupon = couponService.getCoupon(savedCoupon.getId());

            // 캐시로 인해 db 레포는 호출되지 않아야 함
            verify(couponRepository, never()).fetchById(coupon.getId());
            assertThat(savedCoupon.getId()).isEqualTo(cachedCoupon.getId());
        }
    }
}
