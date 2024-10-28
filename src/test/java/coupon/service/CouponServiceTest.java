package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon request = new Coupon(
                "쿠폰이름",
                9000,
                500,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        final CouponEntity coupon = couponService.createCoupon(request);
        final Coupon actual = couponService.getCouponForAdmin(coupon.getId());
        assertThat(actual).isNotNull();
    }
}
