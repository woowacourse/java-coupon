package coupon.coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        // given
        Coupon coupon = new Coupon("name", 1000, 5000, Category.FOOD,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        // when
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
