package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import java.time.LocalDateTime;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

@SpringBootTest
@DisplayName("쿠폰 서비스")
class CouponServiceTest {

    private final CouponService couponService;
    private final CacheManager cacheManager;

    @Autowired
    public CouponServiceTest(CouponService couponService, CacheManager cacheManager) {
        this.couponService = couponService;
        this.cacheManager = cacheManager;
    }

    @DisplayName("쿠폰 서비스 복제 지연 테스트")
    @Test
    void testReplicaReg() {
        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );
        couponService.createCoupon(coupon);

        Coupon savedCoupon = couponService.readCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("쿠폰 서비스는 쿠폰을 생성할 때 RedisCache에 정보를 적재한다.")
    @Test
    void putCacheWhenCreateCoupon() {
        // given
        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );

        // when
        Coupon savedCoupon = couponService.createCoupon(coupon);

        // then
        ValueWrapper actual = Objects.requireNonNull(cacheManager.getCache("coupons"))
                .get(savedCoupon.getId());
        assertThat(actual).isNotNull();
    }

    @DisplayName("쿠폰 서비스는 쿠폰을 읽을 때 RedisCache에서 정보를 꺼내온다.")
    @Test
    void hitCacheWhenReadCoupon() {
        // given
        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );
        Coupon savedCoupon = couponService.createCoupon(coupon);

        // when
        Object actual = cacheManager.getCache("coupons")
                .get(savedCoupon.getId())
                .get();
        Coupon readCoupon = couponService.readCoupon(savedCoupon.getId());

        // then
        assertThat(actual).isEqualTo(readCoupon);
    }
}
