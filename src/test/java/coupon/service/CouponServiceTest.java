package coupon.service;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.entity.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CouponRepository couponRepository;

    private Coupon testCoupon = new Coupon("테스트 쿠폰", List.of(), 1000, 10000,
            Category.ELECTRONICS, LocalDate.of(2024, 10, 24),
            LocalDate.of(2024, 10, 26));

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        Cache cache = cacheManager.getCache("coupons");
        if (cache != null) {
            cache.clear();
        }
    }

    @Test
    void 복제지연테스트() {
        CouponEntity couponEntity = couponService.create(testCoupon);
        CouponResponse couponResponse = couponService.getCoupon(couponEntity.getId());
        assertThat(couponResponse).isNotNull();
    }

    @DisplayName("쿠폰 첫 조회시 캐시에 저장된다")
    @Test
    void saveCacheFirst() {
        CouponEntity couponEntity = couponService.create(testCoupon);
        CouponResponse couponResponse = couponService.getCoupon(couponEntity.getId());

        CouponResponse cacheCoupon = cacheManager.getCache("coupons").get(couponResponse.id(), CouponResponse.class);

        assertAll(
                () -> assertThat(cacheCoupon.id()).isEqualTo(couponEntity.getId()),
                () -> assertThat(cacheCoupon.category()).isEqualTo(testCoupon.getCategory()),
                () -> assertThat(cacheCoupon.discountAmount()).isEqualTo(testCoupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.minimumOrderAmount()).isEqualTo(testCoupon.getMinimumOrderAmount()),
                () -> assertThat(cacheCoupon.name()).isEqualTo(testCoupon.getName()),
                () -> assertThat(cacheCoupon.startDate()).isEqualTo(testCoupon.getStartDate()),
                () -> assertThat(cacheCoupon.endDate()).isEqualTo(testCoupon.getEndDate())
        );
    }

    @DisplayName("한번 캐싱이 되면 DB가 삭제되어도 캐싱된 값을 반환한다")
    @Test
    void saveCache() {
        CouponEntity couponEntity = couponService.create(testCoupon);
        couponService.getCoupon(couponEntity.getId());
        couponRepository.deleteAll();

        CouponResponse cacheCoupon = couponService.getCoupon(couponEntity.getId());

        assertAll(
                () -> assertThat(cacheCoupon.id()).isEqualTo(couponEntity.getId()),
                () -> assertThat(cacheCoupon.category()).isEqualTo(testCoupon.getCategory()),
                () -> assertThat(cacheCoupon.discountAmount()).isEqualTo(testCoupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.minimumOrderAmount()).isEqualTo(testCoupon.getMinimumOrderAmount()),
                () -> assertThat(cacheCoupon.name()).isEqualTo(testCoupon.getName()),
                () -> assertThat(cacheCoupon.startDate()).isEqualTo(testCoupon.getStartDate()),
                () -> assertThat(cacheCoupon.endDate()).isEqualTo(testCoupon.getEndDate())
        );
    }
}
