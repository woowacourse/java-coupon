package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Disabled
    @Test
    void replicationLagTest() {
        Coupon coupon = new Coupon("coupon", 1000, 10000, Category.FOODS, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        couponService.create(coupon);
        assertThatThrownBy(() -> couponService.getCoupon(coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readWithoutLagTest() {
        Coupon coupon = new Coupon("coupon", 1000, 10000, Category.FOODS, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCouponWithoutLag(coupon.getId());
        assertThat(savedCoupon.getId()).isEqualTo(coupon.getId());
    }
}
