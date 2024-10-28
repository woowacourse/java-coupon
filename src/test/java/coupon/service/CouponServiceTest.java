package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponRepository;
import java.time.LocalDateTime;
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

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        resetAutoIncrement("member_coupon");
        resetAutoIncrement("coupon");

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

    @Autowired
    CacheManager cacheManager;

    @DisplayName("레디스 캐시 테스트")
    @Test
    void cacheTest() {
        Coupon savedCoupon = couponRepository.save(coupon);

        couponService.getCoupon(savedCoupon.getId());
        couponService.getCoupon(savedCoupon.getId());

        System.out.println(cacheManager.getCache("coupon").get(savedCoupon.getId(), Coupon.class));
    }
}
