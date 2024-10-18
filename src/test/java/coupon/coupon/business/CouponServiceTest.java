package coupon.coupon.business;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.entity.CouponEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void replicationLagTest() {
        Coupon coupon = new Coupon("가을 맞이 쿠폰", 1000, 10000,
                Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7));
        CouponEntity couponEntity = couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(couponEntity.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
