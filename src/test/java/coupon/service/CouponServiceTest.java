package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.DiscountAmount;
import coupon.domain.IssuancePeriod;
import coupon.domain.MinOrderAmount;
import coupon.exception.CouponException;
import coupon.repository.CouponEntity;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰을 저장한다.")
    void createCoupon() {
        //given
        final CouponName name = new CouponName("레디레디");
        final DiscountAmount discountAmount = new DiscountAmount(5000);
        final MinOrderAmount minOrderAmount = new MinOrderAmount(5000);
        final IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.now(), LocalDate.now().plusDays(3));
        final Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, issuancePeriod);

        //when
        final CouponEntity savedCoupon = couponService.create(coupon);

        //then
        assertThat(savedCoupon).isNotNull();
        assertThat(savedCoupon.getName()).isEqualTo(name.getValue());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 쿠폰을 조회시 예외가 발생한다.")
    void couponNotFound() {
        assertThatThrownBy(() -> couponService.getCoupon(10000000))
                .isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("복제 지연")
    void replicationDelay() {
        //given
        final CouponName name = new CouponName("레디레디");
        final DiscountAmount discountAmount = new DiscountAmount(5000);
        final MinOrderAmount minOrderAmount = new MinOrderAmount(5000);
        final IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.now(), LocalDate.now().plusDays(3));
        final Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, issuancePeriod);
        final CouponEntity created = couponService.create(coupon);

        //when
        final CouponEntity findCoupon = couponService.getCoupon(created.getId());

        //then
        assertThat(findCoupon).isNotNull();
    }
}
