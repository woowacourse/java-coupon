package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.CouponFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceReplicationTest {

    private static final Coupon COUPON = CouponFixture.CREATED_COUPON;

    @Autowired
    private CouponService couponService;

    @Test
    void replicationLagTest() {
        Coupon coupon = COUPON;
        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        assertThat(savedCoupon).isNotNull();
    }
}
