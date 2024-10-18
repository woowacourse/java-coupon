package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("복제 지연으로 인한 이슈를 확인한다.")
    @Test
    void checkReplicationLag() {
        Coupon coupon = new Coupon(1000, 10000);
        couponService.save(coupon);
        Coupon savedCoupon = couponService.findById(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
