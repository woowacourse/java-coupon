package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("복제 지연으로 쿠폰이 조회되지 않는 현상을 방지한다.")
    void occurReplicationLag() {
        Coupon coupon = new Coupon("쿠폰이름", 1_000, 30_000, Category.FASHION, LocalDateTime.now(),
                LocalDateTime.now());

        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCouponInReplicationLag(coupon.getId());

        assertThat(savedCoupon).isNotNull();
    }
}
