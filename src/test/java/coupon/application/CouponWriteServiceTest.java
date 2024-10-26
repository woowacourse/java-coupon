package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssuancePeriod;
import coupon.domain.coupon.MinimumOrderAmount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponWriteServiceTest {

    @Autowired
    private CouponWriteService couponWriteService;

    @Autowired
    private CouponReadService couponReadService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(
                1L,
                new CouponName("쿠폰"),
                new DiscountAmount(new BigDecimal(1_000)),
                new MinimumOrderAmount(new BigDecimal(30_000)),
                Category.FOOD,
                new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        couponWriteService.create(coupon);
        Coupon savedCoupon = couponReadService.getCoupon(coupon.getId());

        assertThat(savedCoupon).isNotNull();
    }
}
