package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssueDuration;
import coupon.domain.coupon.MinimumOrderAmount;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CouponServiceTest extends ServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("복제지연 테스트")
    @Test
    void replicationLag() {
        Coupon coupon = new Coupon(
                new CouponName("쿠폰1"),
                new DiscountAmount("1000"),
                new MinimumOrderAmount("30000"),
                Category.ELECTRONICS,
                new IssueDuration(LocalDateTime.of(2024, 10, 1, 0, 0, 0), LocalDateTime.of(2024, 10, 31, 0, 0, 0))
        );
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());

        assertThat(savedCoupon).isNotNull();
    }
}
