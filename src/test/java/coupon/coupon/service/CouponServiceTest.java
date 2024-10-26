package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Coupon;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("캐시를 사용해 복제 지연을 해결할 수 있다.")
    @Test
    void testReplicationDelay() {
        // given
        Coupon coupon = new Coupon("name", 1000L, 10000L, "FOOD", LocalDateTime.now(), LocalDateTime.now());

        // when
        couponService.createWithCache(coupon);
        Coupon savedCoupon = couponService.readByIdFromReaderWithCache(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
