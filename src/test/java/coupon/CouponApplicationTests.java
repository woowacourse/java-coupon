package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CouponApplicationTests {


    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponQueryService couponQueryService;

    @Test
    void contextLoads() {
    }

    @Test
    void findCouponForPresident() {
        Coupon coupon = couponService.createCoupon(new Coupon("쿠폰1"));
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    void findCouponForCustomer() {
        Coupon coupon = couponService.createCoupon(new Coupon("쿠폰1"));
        Coupon savedCoupon = couponQueryService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNull(); // 지연되도 괜찮다고 판단.
    }

}
