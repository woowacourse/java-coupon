package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.fixture.CouponFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    private Coupon coupon;

    @BeforeEach
    void setup() {
        coupon = new Coupon("daon", 3_000, 100_000, Category.FOOD, LocalDate.now(), LocalDate.now());
        couponRepository.save(coupon);
    }

    @AfterEach
    void teardown() {
        couponRepository.deleteAllInBatch();
    }

    @DisplayName("id로 조회한다.")
    @Test
    void findById() throws InterruptedException {
        Thread.sleep(2000);
        Coupon foundCoupon = couponRepository.findById(coupon.getId())
                .orElseThrow();
        assertThat(foundCoupon).isNotNull();
    }

    @DisplayName("복제 지연 테스트")
    @Test
    void replicaDelayTest() {
        Coupon coupon = CouponFixture.PRAM_COUPON.coupon();
        Coupon savedCoupon = couponService.create(coupon);
        Coupon result = couponService.getCoupon(savedCoupon.getId());
        assertThat(result).isNotNull();
    }
}
