package coupon;

import coupon.application.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
//        Coupon coupon = new Coupon(1000, 10000);
//        couponService.create(coupon);
//        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
//        assertThat(savedCoupon).isNotNull();
    }
}
