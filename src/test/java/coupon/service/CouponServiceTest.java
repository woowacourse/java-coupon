package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() throws InterruptedException {
        Coupon coupon = new Coupon(
                "쿠폰",
                1000,
                Category.FOOD,
                10000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1L)
        );
        Coupon savedCoupon = couponService.create(coupon);
        Coupon foundCoupon   = couponService.findCoupon(savedCoupon.getId());
        assertThat(foundCoupon).isNotNull();
    }
}
