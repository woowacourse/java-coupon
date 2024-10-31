package coupon.coupon.service;

import coupon.coupon.dto.CouponRequest;
import coupon.coupon.dto.CouponResponse;
import coupon.fixture.CouponFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        CouponRequest request = CouponFixture.COUPON_CREATE_REQUEST();

        CouponResponse response = couponService.create(request);

        assertThat(couponService.getCoupon(response.id())).isNotNull();
    }

    @Test
    @DisplayName("쿠폰을 생성할 수 있다.")
    void create() {
        CouponRequest request = CouponFixture.COUPON_CREATE_REQUEST();

        CouponResponse response = couponService.create(request);

        assertThat(response).isNotNull();
    }
}
