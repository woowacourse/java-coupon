package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제_지연_테스트() throws Exception {
        Coupon coupon = new Coupon("망쵸 쿠폰", 1000, 10000);
        couponService.create(coupon);
        Thread.sleep(2000); // UI에서 쿠폰을 조회하는 시간을 지연
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
