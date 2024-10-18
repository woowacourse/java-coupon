package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() throws InterruptedException {
        Coupon coupon = new Coupon("쿠폰", 1000, 10000, Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        couponService.create(coupon);
        Coupon savedCoupon = couponService.read(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
