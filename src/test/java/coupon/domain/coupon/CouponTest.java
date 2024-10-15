package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import coupon.support.data.CouponTestData;
import coupon.support.data.CouponTestData.CouponBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("할인율이 3% 미만이면 예외가 발생한다.")
    @Test
    void createCouponWithDiscountRateLowerThanMinimum() {
        CouponBuilder builder = CouponTestData.defaultCoupon()
                .withDiscountAmount(new DiscountAmount(BigDecimal.valueOf(1000)))
                .withMinimumOrderAmount(new MinimumOrderAmount(BigDecimal.valueOf(35000)));

        assertThatThrownBy(builder::build)
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DISCOUNT_RATE.getMessage());
    }

    @DisplayName("할인율이 20%을 초과하면 예외가 발생한다.")
    @Test
    void createCouponWithDiscountRateGreaterThanMaximum() {
        CouponBuilder builder = CouponTestData.defaultCoupon()
                .withDiscountAmount(new DiscountAmount(BigDecimal.valueOf(1500)))
                .withMinimumOrderAmount(new MinimumOrderAmount(BigDecimal.valueOf(5000)));

        assertThatThrownBy(builder::build)
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DISCOUNT_RATE.getMessage());
    }
}
