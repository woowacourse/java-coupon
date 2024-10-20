package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        // given
        String name = "냥인의쿠폰";
        BigDecimal discountAmount = BigDecimal.valueOf(1_000);
        BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
        CouponCategory couponCategory = CouponCategory.FOOD;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999_999_000);
        Coupon coupon = new Coupon(
                name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt
        );

        // when
        couponService.createCoupon(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
