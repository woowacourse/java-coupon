package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.CouponPeriod;
import coupon.domain.DiscountAmount;
import coupon.domain.MinimumOrderAmount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(new CouponName("zeze"), new DiscountAmount(1000, 5000),
                new MinimumOrderAmount(5000), Category.FASHION, new CouponPeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        Assertions.assertThat(savedCoupon).isNotNull();
    }
}
