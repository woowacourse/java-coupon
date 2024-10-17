package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRateTest {

    @DisplayName("할인율을 생성한다.")
    @Test
    void create() {
        DiscountAmount discountAmount = new DiscountAmount("1000");
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount("30000");

        assertThatCode(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인율이 3% 미만이면 예외가 발생한다.")
    @Test
    void create_Fail1() {
        DiscountAmount discountAmount = new DiscountAmount("1000");
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount("40000");

        assertThatThrownBy(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("할인율이 20% 초과이면 예외가 발생한다.")
    @Test
    void create_Fail2() {
        DiscountAmount discountAmount = new DiscountAmount("8000");
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount("38000");

        assertThatThrownBy(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class);
    }


}
