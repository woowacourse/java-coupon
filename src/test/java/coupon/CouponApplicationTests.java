package coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CouponApplicationTests {

    @Autowired
    CouponService couponService;

    @Test
    void contextLoads() throws InterruptedException {
        final Coupon coupon = new Coupon(
                "추석 할인 쿠폰",
                new Money(new BigDecimal(1000)),
                new Money(new BigDecimal(30000)),
                Category.FOOD,
                LocalDateTime.of(2024, 9, 1, 0, 0),
                LocalDateTime.of(2024, 9, 30, 23, 59)
        );
        couponService.create(coupon);
        final Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
