package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
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
    private CouponRepository couponRepository;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void tearDown() {
        cacheManager.getCache("coupons").clear();
        couponRepository.deleteAll();
    }

    @DisplayName("쿠폰을 저장한다.")
    @Test
    void create() {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon("coupon", 1000, 10000, now, now, Category.FASHION);

        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertAll(
                () -> assertThat(savedCoupon.getName()).isEqualTo("coupon"),
                () -> assertThat(savedCoupon.getDiscount()).isEqualTo(1000),
                () -> assertThat(savedCoupon.getMinAmount()).isEqualTo(10000),
                () -> assertThat(savedCoupon.getStartDate()).isEqualTo(now),
                () -> assertThat(savedCoupon.getEndDate()).isEqualTo(now)
        );
    }

    @DisplayName("쿠폰을 찾을 수 없으면 예외가 발생한다.")
    @Test
    void couponNotFound() {
        assertThatThrownBy(() -> couponService.getCoupon(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("쿠폰을 조회한다.")
    @Test
    void getCoupon() {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon("coupon", 1000, 10000, now, now, Category.FASHION);
        couponService.create(coupon);

        Coupon foundCoupon = couponService.getCoupon(coupon.getId());

        assertThat(foundCoupon).isNotNull();
    }

    @DisplayName("조회한 쿠폰이 캐시에 없는 경우 캐시를 업데이트한다")
    @Test
    void getCouponAndCache() throws InterruptedException {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon("coupon", 1000, 10000, now, now, Category.FASHION);
        couponService.create(coupon);
        cacheManager.getCache("coupons").evict(coupon.getId());
        Thread.sleep(3000);

        Coupon foundCoupon = couponService.getCoupon(coupon.getId());

        assertThat(foundCoupon).isNotNull();
        assertThat(cacheManager.getCache("coupons").get(coupon.getId())).isNotNull();
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
