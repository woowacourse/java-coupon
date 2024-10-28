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
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(8)
        );
        CouponEntity couponEntity = couponService.create(coupon);
        CouponResponse couponResponse = couponService.getCoupon(couponEntity.getId());
        assertThat(couponResponse).isNotNull();
    }

    @DisplayName("쿠폰 첫 조회시 캐시에 저장된다.")
    @Test
    void saveCacheFirst() {
        LocalDate today = LocalDate.now();
        Coupon coupon = new Coupon(
                "nyangin",
                List.of(),
                1000,
                5000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        );
        CouponEntity couponEntity = couponService.create(coupon);
        CouponResponse couponResponse = couponService.getCoupon(couponEntity.getId());

        CouponResponse cacheCoupon = cacheManager.getCache("coupons").get(couponResponse.id(), CouponResponse.class);

        assertAll(
                () -> assertThat(cacheCoupon.id()).isEqualTo(couponEntity.getId()),
                () -> assertThat(cacheCoupon.category()).isEqualTo(coupon.getCategory()),
                () -> assertThat(cacheCoupon.discountAmount()).isEqualTo(coupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.minimumOrderAmount()).isEqualTo(coupon.getMinimumOrderAmount()),
                () -> assertThat(cacheCoupon.name()).isEqualTo(coupon.getName()),
                () -> assertThat(cacheCoupon.startDate()).isEqualTo(coupon.getStartDate()),
                () -> assertThat(cacheCoupon.endDate()).isEqualTo(coupon.getEndDate())
        );
    }

    @DisplayName("쿠폰 조회 2번째부터는 캐시에서 조회되므로 DB가 삭제되어도 캐싱된 값을 반환한다.")
    @Test
    void saveCache() {
        LocalDate today = LocalDate.now();
        Coupon coupon = new Coupon(
                "nyangin",
                List.of(),
                1000,
                5000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        );

        CouponEntity couponEntity = couponService.create(coupon);
        couponService.getCoupon(couponEntity.getId());
        couponRepository.deleteAll();

        CouponResponse cacheCoupon = couponService.getCoupon(couponEntity.getId());

        assertAll(
                () -> assertThat(cacheCoupon.id()).isEqualTo(couponEntity.getId()),
                () -> assertThat(cacheCoupon.category()).isEqualTo(coupon.getCategory()),
                () -> assertThat(cacheCoupon.discountAmount()).isEqualTo(coupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.minimumOrderAmount()).isEqualTo(coupon.getMinimumOrderAmount()),
                () -> assertThat(cacheCoupon.name()).isEqualTo(coupon.getName()),
                () -> assertThat(cacheCoupon.startDate()).isEqualTo(coupon.getStartDate()),
                () -> assertThat(cacheCoupon.endDate()).isEqualTo(coupon.getEndDate())
        );
    }
}
