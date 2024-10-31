package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import java.util.Objects;
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
    private CacheManager cacheManager;

    @Test
    void 복제지연테스트() {
        CouponRequest request = new CouponRequest("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon savedCoupon = couponService.createCoupon(request);
        Coupon foundCoupon = couponService.getCoupon(savedCoupon.getId());
        assertThat(foundCoupon).isNotNull();
    }

    @Test
    @DisplayName("쿠폰은 생성할 때 캐시 저장소에도 저장하는 Write Through 캐싱 전략을 활용한다.")
    void cacheWrite() {
        CouponRequest request = new CouponRequest("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon savedCoupon = couponService.createCoupon(request);

        assertThat(Objects.requireNonNull(cacheManager.getCache("coupon")).get(savedCoupon.getId()))
                .isNotNull();
    }

    @Test
    @DisplayName("쿠폰은 조회할 때 캐시 저장소를 먼저 조회한다.")
    void cacheRead() {
        CouponRequest request = new CouponRequest("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon savedCoupon = couponService.createCoupon(request);

        Coupon findCoupon = couponService.getCoupon(savedCoupon.getId());
        Object cachedCoupon = cacheManager.getCache("coupon")
                .get(findCoupon.getId())
                .get();

        assertThat(cachedCoupon).isEqualTo(findCoupon);
    }
}
