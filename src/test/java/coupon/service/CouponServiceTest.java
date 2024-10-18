package coupon.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Coupon;
import coupon.fixture.CouponFixture;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        // given
        Coupon coupon = CouponFixture.create();
        couponService.create(coupon);

        // when
        Coupon foundCoupon = couponService.getCoupon(coupon.getId());

        // then
        assertThat(foundCoupon).isNotNull();
    }
}
