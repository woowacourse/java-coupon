package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("복제 지연 테스트")
    @Test
    void replicationLag() {
        Long couponId = couponService.createCoupon();
        Coupon savedCoupon = couponService.findCoupon(couponId);
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    void findById() {
        Coupon coupon = couponService.findCoupon(1L);
        assertThat(coupon).isNotNull();
    }
}
