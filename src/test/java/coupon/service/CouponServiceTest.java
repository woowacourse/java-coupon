package coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.dto.CouponRequest;
import coupon.dto.CouponResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("복제 지연 테스트")
    void replicationLagTest() {
        CouponRequest couponRequest = new CouponRequest(
                "쿠폰1",
                10000L,
                2000L,
                1L,
                LocalDate.now(),
                LocalDate.now().plusDays(7));

        CouponResponse couponResponse = couponService.create(couponRequest);
        assertThatCode(() -> couponService.getCoupon(couponResponse.id()))
                .doesNotThrowAnyException();
    }
}
