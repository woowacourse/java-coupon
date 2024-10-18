package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.dto.request.CouponRequest;


@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        CouponRequest couponRequest = new CouponRequest(
                "kk", 1000, 30000, Category.FOOD, LocalDateTime.now(), LocalDateTime.now());
        CouponEntity coupon = couponService.create(couponRequest);
        CouponEntity savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}

