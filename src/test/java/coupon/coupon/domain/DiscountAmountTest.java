package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @Test
    @DisplayName("할인 금액이 1,000원 미만이면 예외가 발생한다.")
    void should_throw_exception_when_discount_amount_less_than_1000() {
        // given
        int discountAmount = 999;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 10,000원 초과면 예외가 발생한다.")
    void should_throw_exception_when_discount_amount_more_than_10000() {
        // given
        int discountAmount = 10001;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 10,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    void should_throw_exception_when_discount_amount_unit_not_500() {
        // given
        int discountAmount = 9999;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로만 설정할 수 있습니다.");
    }
}
