package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponServiceTest extends ServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 쿠폰을_생성할_수_있다() {
        // given
        Coupon coupon = new Coupon(
                "점심 반값 쿠폰",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );

        // when & then
        assertThat(couponService.create(coupon)).isEqualTo(1L);
    }
}
