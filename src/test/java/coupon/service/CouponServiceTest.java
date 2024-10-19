package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.service.dto.CreateCouponRequest;
import coupon.service.dto.CreateCouponResponse;
import coupon.service.dto.GetCouponResponse;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        CreateCouponRequest request = new CreateCouponRequest(
                "쿠폰이름",
                9000,
                500,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        final CreateCouponResponse createCouponResponse = couponService.createCoupon(request);
        final GetCouponResponse getCouponResponse = couponService.getCoupon(createCouponResponse.id());
        assertThat(getCouponResponse).isNotNull();
    }
}
