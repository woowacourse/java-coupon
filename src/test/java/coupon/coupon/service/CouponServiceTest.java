package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.util.CouponFixture;
import coupon.util.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CouponServiceTest extends ServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = CouponFixture.createValidFoodCoupon();
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCouponById(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
