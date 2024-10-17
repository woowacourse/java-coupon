package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰을 조회한다.")
    void getCoupon() {
        Coupon coupon = new Coupon(
                "coupon",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(1000),
                "Food",
                LocalDate.now(),
                LocalDate.now()
        );
        Coupon save = couponRepository.save(coupon);

        Coupon findCoupon = couponService.getCouponImmediately(save.getId());

        assertAll(
                () -> assertThat(findCoupon.getId()).isNotNull(),
                () -> assertThat(findCoupon.getName()).isEqualTo("coupon")
        );
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 조회하면 예외가 발생한다.")
    void getCouponWhenNonExistentCoupon() {
        Long notExistCouponId = 9999999L;

        assertThatThrownBy(() -> couponService.getCoupon(notExistCouponId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("쿠폰을 생성한다.")
    void createCoupon() {
        Coupon coupon = new Coupon(
                "coupon",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(1000),
                "Food",
                LocalDate.now(),
                LocalDate.now()
        );

        Coupon save = couponService.createCoupon(coupon);

        assertThat(save.getName()).isEqualTo("coupon");
    }
}
