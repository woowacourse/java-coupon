package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.dto.request.CouponRequest;
import coupon.coupon.repository.CouponRepository;


@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
    }

    @Test
    void 복제지연테스트() {
        CouponRequest couponRequest = new CouponRequest(
                "1000원 할인 쿠폰",
                1000,
                30000,
                Category.FOOD,
                LocalDateTime.of(2024, 10, 17, 10, 10),
                LocalDateTime.of(2024, 10, 18, 10, 10));
        CouponEntity coupon = couponService.create(couponRequest);
        CouponEntity savedCoupon = couponService.getCouponById(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}

