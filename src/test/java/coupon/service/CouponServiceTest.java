package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponWriterService couponWriterService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon savedCoupon = couponWriterService.create(coupon);
        Coupon foundCoupon = couponService.getCoupon(savedCoupon.getId());
        assertThat(foundCoupon).isNotNull();
    }
}
