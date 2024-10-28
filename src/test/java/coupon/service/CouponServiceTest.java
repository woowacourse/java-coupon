package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @AfterEach
    void tearDown() {
        Objects.requireNonNull(redisCacheManager.getCache("coupon")).clear();
        couponRepository.deleteAll();
    }

    @Test
    void 복제_지연_테스트() {
        Coupon coupon = couponService.create(Coupon.builder()
                .name("coupon")
                .discountAmount(1000)
                .minimumOrderAmount(5000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build());

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("쿠폰 생성시 캐시에도 저장한다.")
    @Test
    void create_withCache() {
        // given
        Coupon coupon = Coupon.builder()
                .name("coupon")
                .discountAmount(1000)
                .minimumOrderAmount(10000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build();

        // when
        Coupon savedCoupon = couponService.create(coupon);

        // then
        Cache couponCache = Objects.requireNonNull(redisCacheManager.getCache("coupon"));
        Coupon fromCache = couponCache.get(savedCoupon.getId(), Coupon.class);
        assertThat(fromCache).isNotNull();
        assertThat(fromCache.getId()).isEqualTo(savedCoupon.getId());
    }

    @DisplayName("쿠폰 조회시 캐시에 데이터가 없으면 DB에서 조회한 뒤 캐시에 저장한다.")
    @Test
    void getCoupon_whenCacheMiss() {
        // given
        Coupon coupon = couponRepository.save(Coupon.builder()
                .name("coupon")
                .discountAmount(1000)
                .minimumOrderAmount(10000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build());
        Coupon fromCache = Objects.requireNonNull(redisCacheManager.getCache("coupon"))
                .get(coupon.getId(), Coupon.class);
        assertThat(fromCache).isNull();

        // when
        couponService.getCoupon(coupon.getId());
        fromCache = Objects.requireNonNull(redisCacheManager.getCache("coupon"))
                .get(coupon.getId(), Coupon.class);

        // then
        assertThat(fromCache).isNotNull();
        assertThat(fromCache.getId()).isEqualTo(coupon.getId());
    }
}
