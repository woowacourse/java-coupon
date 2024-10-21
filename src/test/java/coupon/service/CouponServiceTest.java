package coupon.service;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.entity.CouponEntity;
import coupon.coupon.service.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(8)
        );
        CouponEntity couponEntity = couponService.create(coupon);
        CouponEntity savedCoupon = couponService.getCoupon(couponEntity.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
