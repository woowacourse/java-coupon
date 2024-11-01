package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.entity.CouponEntity;

@Transactional
@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("writer DB 접근 후 쿠폰 조회에 실패하면 reader DB에 접근해서 쿠폰을 조회한다.")
    @Test
    void get_coupon_for_admin() {
        Coupon request = new Coupon(
                "쿠폰이름",
                9000,
                500,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        final CouponEntity coupon = couponService.createCoupon(request);
        final Coupon actual = couponService.getCoupon(coupon.getId());
        assertThat(actual).isNotNull();
    }

    @DisplayName("쿠폰 조회에 성공할 때까지 최대 2번 reader DB에 접근해서 쿠폰을 조회한다.")
    @Test
    void get_coupon_for_member() {
        Coupon request = new Coupon(
                "쿠폰이름",
                9000,
                500,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        final CouponEntity coupon = couponService.createCoupon(request);
        final Coupon actual = couponService.getCoupon(coupon.getId());
        assertThat(actual).isNotNull();
    }
}
