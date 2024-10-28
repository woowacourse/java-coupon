package coupon.coupon.service;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import coupon.CouponException;
import coupon.ServiceTest;
import coupon.coupon.domain.Coupon;
import coupon.fixture.CouponFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CouponServiceTest extends ServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("요청한 쿠폰을 조회한다.")
    @Test
    void getCoupon() {
        // given
        Coupon coupon = CouponFixture.create(LocalDate.now(), LocalDate.now().plusDays(7));
        couponRepository.save(coupon);

        // when
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 조회하면 예외가 발생한다.")
    void cannotGetCoupon() {
        // given
        long notExistCouponId = 0;

        // when & then
        assertThatThrownBy(() -> couponService.getCoupon(notExistCouponId))
                .isInstanceOf(CouponException.class)
                .hasMessage("요청하신 쿠폰을 찾을 수 없어요.");
    }

    @Test
    @DisplayName("쿠폰을 생성한다.")
    void createCoupon() {
        // given
        Coupon coupon = CouponFixture.create(LocalDate.now(), LocalDate.now().plusDays(7));

        // when
        couponService.create(coupon);

        // then
        assertThat(couponRepository.findById(coupon.getId())).isNotNull();
    }
}

