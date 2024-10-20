package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제_지연_테스트() {
        Coupon coupon = couponService.create(Coupon.builder()
                .name("coupon")
                .discountAmount(1000)
                .minimumOrderAmount(5000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build());

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
