package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Coupon;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("캐시를 사용해 생성한 쿠폰을 복제 지연 문제 없이 불러올 수 있다.")
    @Test
    void testCreateAndReadCouponWithCache() {
        // given
        Coupon coupon = new Coupon("name", 1000L, 10000L, "FOOD", LocalDateTime.now(), LocalDateTime.now());

        // when
        couponService.createWithCache(coupon);
        Optional<Coupon> savedCoupon = couponService.readByIdFromReaderWithCache(coupon.getId());

        // then
        assertThat(savedCoupon).isPresent();
    }
}
