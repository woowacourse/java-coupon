package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        cacheManager.getCache("coupons").clear();
    }

    @DisplayName("캐시가 없는 경우 복제 지연으로 인해 쿠폰을 조회하지 못한다.")
    @Test
    void replicationDelayWithoutCache() {
        Coupon coupon = new Coupon("coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.FOOD);
        couponService.create(coupon);
        cacheManager.getCache("coupons").clear();

        assertThatThrownBy(() -> couponService.getCoupon(coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("캐시에 쿠폰을 저장해 복제 지연 문제를 해결한다.")
    @Test
    void replicationDelay() {
        Coupon coupon = new Coupon("coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.FOOD);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
