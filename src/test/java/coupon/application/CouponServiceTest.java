package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.dto.CouponResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        Cache cache = cacheManager.getCache("coupons");
        if (cache != null) {
            cache.clear();
        }
    }

    @Test
    void 복제지연테스트() throws Exception {
        Coupon coupon = new Coupon("CouponName", 1_000, 10_000, "가구", LocalDate.now(), LocalDate.now());
        couponService.create(coupon);
        CouponResponse savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("Writer DB에도 없으면 지정한 예외를 반환한다.")
    @Test
    void 복제지연테스트_예외() throws Exception {
        assertThatThrownBy(() -> couponService.getCoupon(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("쿠폰 첫 조회시 캐시에 저장된다.")
    @Test
    void saveCacheFirst() {
        Coupon coupon = new Coupon("CouponName", 1_000, 10_000, "가구", LocalDate.now(), LocalDate.now());
        Long id = couponService.create(coupon);
        CouponResponse coupon1 = couponService.getCoupon(id);

        CouponResponse cacheCoupon = cacheManager.getCache("coupons").get(id, CouponResponse.class);

        assertAll(
                () -> assertThat(cacheCoupon.id()).isEqualTo(id),
                () -> assertThat(cacheCoupon.category()).isEqualTo(coupon.getCategory()),
                () -> assertThat(cacheCoupon.discountAmount()).isEqualTo(coupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.minOrderAmount()).isEqualTo(coupon.getMinOrderAmount()),
                () -> assertThat(cacheCoupon.name()).isEqualTo(coupon.getName()),
                () -> assertThat(cacheCoupon.startDate()).isEqualTo(coupon.getStartDate()),
                () -> assertThat(cacheCoupon.endDate()).isEqualTo(coupon.getEndDate())
        );
    }

    @DisplayName("쿠폰 조회 2번째부터는 캐시에서 조회되므로 DB가 삭제되어도 캐싱된 값을 반환한다.")
    @Test
    void saveCache() {
        Coupon coupon = new Coupon("CouponName", 1_000, 10_000, "가구", LocalDate.now(), LocalDate.now());
        Long id = couponService.create(coupon);
        CouponResponse coupon1 = couponService.getCoupon(id);
        couponRepository.deleteAll();

        CouponResponse cacheCoupon = couponService.getCoupon(id);

        assertAll(
                () -> assertThat(cacheCoupon.id()).isEqualTo(id),
                () -> assertThat(cacheCoupon.category()).isEqualTo(coupon.getCategory()),
                () -> assertThat(cacheCoupon.discountAmount()).isEqualTo(coupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.minOrderAmount()).isEqualTo(coupon.getMinOrderAmount()),
                () -> assertThat(cacheCoupon.name()).isEqualTo(coupon.getName()),
                () -> assertThat(cacheCoupon.startDate()).isEqualTo(coupon.getStartDate()),
                () -> assertThat(cacheCoupon.endDate()).isEqualTo(coupon.getEndDate())
        );
    }
}
