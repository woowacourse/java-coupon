package coupon.application.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import coupon.support.AcceptanceTestSupport;
import coupon.support.data.CouponTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CouponServiceTest extends AcceptanceTestSupport {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("식별자로 쿠폰을 조회한다.")
    @Test
    void getCoupon() {
        Coupon coupon = couponRepository.save(CouponTestData.defaultCoupon().build());

        Coupon actual = couponService.getCoupon(coupon.getId());

        assertThat(actual).isEqualTo(coupon);
    }

    @DisplayName("존재하지 않는 식별자로 조회시 예외가 발생한다.")
    @Test
    void getCouponByNotExistId() {
        Long given = -1L;

        assertThatThrownBy(() -> couponService.getCoupon(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_NOT_FOUND.getMessage());
    }

    @DisplayName("쿠폰을 저장한다.")
    @Test
    void create() {
        Coupon coupon = CouponTestData.defaultCoupon().build();
        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        assertThat(savedCoupon).isEqualTo(coupon);
    }

    @Test
    void testReplicationLag() {
        Coupon coupon = CouponTestData.defaultCoupon().build();
        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        assertThat(savedCoupon).isNotNull();
    }
}
