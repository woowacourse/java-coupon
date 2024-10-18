package coupon.coupon.service;

import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import coupon.coupon.CouponException;
import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Disabled
    @DisplayName("요청한 쿠폰을 조회한다.")
    @Test
    void getCoupon() {
        // given
        Coupon coupon = new Coupon(
                "linirini",
                1000,
                10000,
                Category.FASHION,
                LocalDate.now(),
                LocalDate.now().plusDays(7));

        // when
        couponService.create(coupon);

        // then
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
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
        Coupon coupon = new Coupon(
                "linirini",
                1000,
                10000,
                Category.FASHION,
                LocalDate.now(),
                LocalDate.now().plusDays(7));

        // when
        couponService.create(coupon);

        // then
        assertThat(couponRepository.findById(coupon.getId())).isNotNull();
    }
}
