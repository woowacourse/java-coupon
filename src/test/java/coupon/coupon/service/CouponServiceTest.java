package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("쿠폰 서비스")
class CouponServiceTest {

    private final CouponService couponService;

    @Autowired
    public CouponServiceTest(CouponService couponService) {
        this.couponService = couponService;
    }

    @DisplayName("쿠폰 서비스 복제 지연 테스트")
    @Test
    void testReplicaReg() {
        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );
        couponService.createCoupon(coupon);

        Coupon savedCoupon = couponService.readCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
