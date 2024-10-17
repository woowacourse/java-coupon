package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Coupon;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("복제지연테스트")
    @Test
    void testReplicationDelay() throws InterruptedException {
        Coupon coupon = new Coupon("name", 1000L, 10000L, "FOOD", LocalDateTime.now(), LocalDateTime.now());
        couponService.create(coupon);

        Coupon savedCoupon = couponService.readByIdFromReader(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
