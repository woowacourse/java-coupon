package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("복제 지연 테스트")
    @Test
    void replicaDelayTest() {
        Coupon coupon = new Coupon("pram", 1000, 30_000, Category.FOOD, LocalDate.now(), LocalDate.now());
        Coupon savedCoupon = couponService.create(coupon);
        Coupon result = couponService.getCoupon(savedCoupon.getId());
        assertThat(result).isNotNull();
    }
}
