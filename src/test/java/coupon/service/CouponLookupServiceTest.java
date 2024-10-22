package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CouponLookupServiceTest {

    @Autowired
    private CouponLookupService couponLookupService;

    @Autowired
    private CouponCache couponCache;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        cacheManager.getCache("coupon").clear();
    }

    @DisplayName("캐시에 존재하는 쿠폰을 조회한다.")
    @Test
    void findByIdWithCache() {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon(1L, "coupon", 1000, 10000, now, now, Category.FASHION);
        couponCache.cache(coupon);

        assertThat(couponLookupService.findById(1L)).isNotNull();
    }

    @DisplayName("캐시에 존재하지 않는 쿠폰을 조회한다.")
    @Test
    void findByIdWithoutCache() throws InterruptedException {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon("coupon", 1000, 10000, now, now, Category.FASHION);
        couponRepository.save(coupon);
        Thread.sleep(2000);

        assertThat(couponLookupService.findById(coupon.getId())).isNotNull();
    }

    @DisplayName("캐시가 존재하지 않으면 예외가 발생한다.")
    @Test
    void couponNotFound() {
        assertThatThrownBy(() -> couponLookupService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("멤버 쿠폰에 대한 쿠폰 목록을 조회한다.")
    @Test
    void findByMemberCoupons() throws InterruptedException {
        LocalDate now = LocalDate.now();
        Coupon cachedCoupon = new Coupon(1L, "cached", 1000, 10000, now, now, Category.FASHION);
        couponCache.cache(cachedCoupon);
        Coupon savedCoupon = couponRepository.save(new Coupon("saved", 1000, 10000, now, now, Category.FOOD));
        Thread.sleep(2000);
        List<MemberCoupon> memberCoupons = List.of(
                new MemberCoupon(cachedCoupon.getId(), 1L, false, LocalDateTime.now()),
                new MemberCoupon(savedCoupon.getId(), 1L, false, LocalDateTime.now())
        );

        List<String> couponNames = couponLookupService.findByMemberCoupons(memberCoupons)
                .stream()
                .map(Coupon::getName)
                .toList();

        assertThat(couponNames).containsExactlyInAnyOrder("cached", "saved");
    }
}
