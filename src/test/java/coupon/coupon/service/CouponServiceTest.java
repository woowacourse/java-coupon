package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import coupon.coupon.domain.DiscountAmount;
import coupon.coupon.domain.IssuablePeriod;
import coupon.coupon.domain.MinimumOrderAmount;
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
        CouponName couponName = new CouponName("쿠폰");
        DiscountAmount discountAmount = new DiscountAmount(10000);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(100000);
        IssuablePeriod issuablePeriod
                = new IssuablePeriod(LocalDate.parse("2024-10-18"), LocalDate.parse("2024-10-18"));

        Coupon coupon = new Coupon(couponName, discountAmount, minimumOrderAmount, Category.FOODS, issuablePeriod);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
