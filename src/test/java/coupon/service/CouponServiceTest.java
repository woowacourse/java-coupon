package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Test
    void 쿠폰을_생성한다() {
        // given
        Coupon coupon = new Coupon("testCoupon", 1000L, 30000L, Category.FASHION);

        // when
        Coupon savedCoupon = couponService.create(coupon);

        // then
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    void 쿠폰을_조회한다() {
        // given
        Coupon coupon = new Coupon("testCoupon", 1000L, 30000L, Category.FASHION);
        Coupon savedCoupon = couponService.create(coupon);

        // when
        Coupon findResult = couponService.getById(savedCoupon.getId());

        // then
        assertThat(findResult).isEqualTo(savedCoupon);
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(1000L, 30000L);
        Coupon saved = couponService.create(coupon);
        Coupon find = couponService.getById(saved.getId());

        assertThat(find).isNotNull();
    }
}
