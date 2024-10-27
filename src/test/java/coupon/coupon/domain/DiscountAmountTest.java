package coupon.coupon.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DiscountAmountTest {

    @DisplayName("할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    @Test
    void cannotCreateIfWrongDiscountAmountUnit() {
        // given
        int discountAmount = 1100;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @DisplayName("할인 금액이 1000원 미만이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountAmountUnder() {
        // given
        int discountAmount = 500;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 1000원 이상, 10000원 이하이어야 합니다.");
    }

    @DisplayName("할인 금액이 10000원 초과이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountAmountOver() {
        // given
        int discountAmount = 10500;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 1000원 이상, 10000원 이하이어야 합니다.");
    }

    @DisplayName("주어진 금액에 대한 할인 금액의 비율을 계산한다.")
    @Test
    void getDiscountRate() {
        // given
        DiscountAmount discountAmount = new DiscountAmount(2500);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(10000);

        // when
        BigDecimal discountRate = discountAmount.getDiscountRate(minimumOrderAmount);

        //then
        assertThat(discountRate.intValue()).isEqualTo(25);
    }
}
