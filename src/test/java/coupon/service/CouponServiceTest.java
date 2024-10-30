package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.util.CouponFixture;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("캐싱 테스트")
    void getCouponFromCache() {
        // given & when
        Coupon savedCoupon = couponService.create(CouponFixture.createCoupon());
        Optional<Coupon> cachedCoupon = couponService.getCoupon(savedCoupon.getId());

        // then
        assertThat(cachedCoupon).isPresent();
    }
}
