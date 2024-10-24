package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.CouponApplication;
import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CouponApplication.class)
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(
                "Test Coupon",
                1000,
                30000,
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        couponService.createCoupon(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
