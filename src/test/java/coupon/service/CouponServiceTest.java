package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = Coupon.createCoupon(
                "테스트 쿠폰",
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(8)
        );
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
