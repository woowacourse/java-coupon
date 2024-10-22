package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountMount;
import coupon.domain.coupon.MinimumMount;
import coupon.domain.coupon.Period;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
