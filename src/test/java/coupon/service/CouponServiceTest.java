package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CacheManager cacheManager;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        resetAutoIncrement("member_coupon");
        resetAutoIncrement("coupon");
        resetAutoIncrement("member");

        coupon = new Coupon(
                "Jazz",
                1000, 10000,
                CouponCategory.FOOD,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                100, 0);
    }

    private void resetAutoIncrement(String tableName) {
        jdbcTemplate.execute("DELETE FROM " + tableName);
        jdbcTemplate.execute("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1");
    }

    @DisplayName("복제 지연 테스트")
    @Test
    void replicationDelayTest() {
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("레디스 캐시 테스트")
    @Test
    void cacheTest() {
        // 캐시 초기화
        Objects.requireNonNull(cacheManager.getCache("coupon")).clear();

        Coupon savedCoupon = couponRepository.save(coupon);

        // 캐시 데이터 NULL
        assertThat(cacheManager.getCache("coupon").get(savedCoupon.getId(), Coupon.class)).isNull();

        // 캐시 미스 및 캐시에 쿠폰 저장
        Coupon firstFetch = couponService.getCoupon(savedCoupon.getId());

        // 캐시에 데이터가 적재되었는지 검증
        Coupon cacheCoupon = cacheManager.getCache("coupon").get(savedCoupon.getId(), Coupon.class);
        assertThat(cacheCoupon).isNotNull();
        assertThat(firstFetch.getId()).isEqualTo(cacheCoupon.getId());

        // 캐시 히트
        Coupon secondFetch = couponService.getCoupon(savedCoupon.getId());
        assertThat(secondFetch.getId()).isEqualTo(cacheCoupon.getId());
    }
}
