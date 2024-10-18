package coupon.service.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import java.time.LocalDateTime;
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
    void replicaDelay() {
        Coupon coupon = new Coupon(
                null,
                "쿠폰",
                1000,
                10000,
                CouponCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}

