package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("복제 지연 테스트")
    @Test
    void replicationDelay() {
        Coupon coupon = new Coupon("coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.FOOD);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
