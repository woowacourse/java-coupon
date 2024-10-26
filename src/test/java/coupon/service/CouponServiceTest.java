package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon("name",
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10000,
                1000);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    void 생성테스트() {
        Coupon coupon = new Coupon("name",
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10000,
                1000);
        couponService.create(coupon);
        assertThat(couponRepository.findAll()).size().isEqualTo(1);
    }

    @Test
    void 쿠폰을_조회한다() {
        Coupon coupon = new Coupon("name",
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10000,
                1000);
        couponService.create(coupon);

        Coupon getCoupon = couponService.getCoupon(coupon.getId());

        assertThat(getCoupon.getId()).isEqualTo(coupon.getId());
    }
}
