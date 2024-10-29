package coupon.service.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import coupon.domain.CouponFixture;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.MemberCoupon;
import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void replicationLagTest() {
        Coupon coupon = CouponFixture.FOOD_COUPON;

        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("한 회원에게 동일한 쿠폰을 5개 발급한다.")
    @Test
    void issue() {
        Coupon coupon = CouponFixture.FOOD_COUPON;
        couponService.create(coupon);

        for (int i = 0; i < 5; i++) {
            MemberCoupon memberCoupon = couponService.issue(1L, coupon.getId());
            assertThat(memberCoupon).isNotNull();
        }
    }

    @DisplayName("한 회원에게 동일한 쿠폰을 6개 이상 발급하면 예외가 발생한다.")
    @Test
    void issueFailed() {
        Coupon coupon = CouponFixture.FOOD_COUPON;
        couponService.create(coupon);
        for (int i = 0; i < 5; i++) {
            couponService.issue(1L, coupon.getId());
        }

        assertThatThrownBy(() -> couponService.issue(1L, coupon.getId()))
                .isInstanceOf(CouponException.class);
    }
}
