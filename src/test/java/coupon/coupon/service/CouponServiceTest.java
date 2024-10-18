package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.CouponFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("쿠폰 서비스 테스트")
@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("쿠폰을 생성하고 저장할 수 있다")
    @Test
    void createCoupon() {
        final var tourootCoupon = CouponFixture.TOUROOT_COUPON.getCoupon();
        final var savedCoupon = couponService.createCoupon(tourootCoupon);

        final var found = couponService.getCoupon(savedCoupon.getId());

        assertThat(savedCoupon.getId()).isNotNull();
    }

    @DisplayName("쿠폰을 조회할 수 있다")
    @Test
    void findCoupon() {
        final var tourootCoupon = CouponFixture.TOUROOT_COUPON.getCoupon();
        final var savedCoupon = couponService.createCoupon(tourootCoupon);

        final var found = couponService.getCoupon(savedCoupon.getId());

        assertThat(found.getName()).isEqualTo("투룻 쿠폰");
    }
}
