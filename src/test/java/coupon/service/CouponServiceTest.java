package coupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.domain.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void replicationLagTest() {
        Coupon coupon = new Coupon(
                "coupon",
                10000,
                1000,
                "FOOD",
                LocalDate.now(),
                LocalDate.now().plusDays(7)
        );

        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
