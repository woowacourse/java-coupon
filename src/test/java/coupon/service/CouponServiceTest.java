package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.fixture.CouponFixture;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        couponRepository.deleteAllInBatch();
    }

    @DisplayName("복제 지연 테스트")
    @Test
    void replicaDelay() {
        Coupon coupon = CouponFixture.TEST_COUPON;
        long id = couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(id);
        assertThat(savedCoupon).isNotNull();
    }
}
