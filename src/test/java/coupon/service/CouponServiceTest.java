package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.ProductCategory;
import coupon.repository.entity.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        // given
        Coupon coupon = new Coupon("name",
                1000,
                10000,
                ProductCategory.패션,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5));

        // when
        couponService.saveCoupon(coupon);

        // then
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
