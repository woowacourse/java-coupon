package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssuancePeriod;
import coupon.domain.coupon.MinimumOrderAmount;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CouponReadServiceTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponReadService couponReadService;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE Coupon AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    @DisplayName("쿠폰 식별자로 쿠폰을 조회한다.")
    @Transactional
    void getCoupon() {
        Coupon coupon = new Coupon(
                1L,
                new CouponName("쿠폰"),
                new DiscountAmount(new BigDecimal(1_000)),
                new MinimumOrderAmount(new BigDecimal(30_000)),
                Category.FOOD,
                new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        Coupon save = couponRepository.save(coupon);

        Coupon actual = couponReadService.getCoupon(save.getId());

        assertThat(actual.getId()).isEqualTo(coupon.getId());
    }
}
