package coupon.coupon.business;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void replicationLagTest() {
        Coupon coupon = new Coupon("가을 맞이 쿠폰", 1000, 10000,
                Category.FASHION, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
