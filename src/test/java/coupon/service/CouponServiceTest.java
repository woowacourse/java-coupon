package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("쿠폰을 생성한다.")
    @Test
    void create() {
        // given
        Coupon coupon = new Coupon("test-coupon", 1000, 30000, LocalDate.now(), LocalDate.now(), Category.FASHION);

        // when
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
