package coupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import java.time.LocalDateTime;
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
    void replicationDelayTest() {
        Coupon coupon = new Coupon(
                "Jazz",
                1000, 10000,
                CouponCategory.FOOD,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                100, 0);

        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
