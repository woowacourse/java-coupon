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
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount("33333");

        assertThatCode(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인율이 3% 미만이면 예외가 발생한다.")
    @Test
    void create_Fail1() {
        DiscountAmount discountAmount = new DiscountAmount("1000");
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount("33334");

        assertThatThrownBy(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("할인율이 20% 초과이면 예외가 발생한다.")
    @Test
    void create_Fail2() {
        //case - 할인율 21%
        DiscountAmount discountAmount = new DiscountAmount("10500");
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount("50000");

        assertThatThrownBy(() -> new DiscountRate(discountAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class);
    }


}
