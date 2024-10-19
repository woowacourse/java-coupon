package coupon.domain;

import coupon.exception.CouponException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @Test
    @DisplayName("할인 금액이 1000원 미만일 때 예외 발생")
    void validateDiscountAmount_TooLow_ThrowsException() {
        // given
        int invalidAmount = 500;
        int minimumOrderAmount = 5000;

        // when & then
        Assertions.assertThatThrownBy(() -> new DiscountAmount(invalidAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 1000원 이상 10,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 10,000원을 초과할 때 예외 발생")
    void validateDiscountAmount_TooHigh_ThrowsException() {
        // given
        int invalidAmount = 10_500;
        int minimumOrderAmount = 5000;

        // when & then
        Assertions.assertThatThrownBy(() -> new DiscountAmount(invalidAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 1000원 이상 10,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아닐 때 예외 발생")
    void validateDiscountAmount_InvalidUnit_ThrowsException() {
        // given
        int invalidAmount = 1300;
        int minimumOrderAmount = 5000;

        // when & then
        Assertions.assertThatThrownBy(() -> new DiscountAmount(invalidAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 500원 단위여야 합니다.");
    }

    @Test
    @DisplayName("할인율이 3% 이상 20% 이하가 아닐 때 예외 발생")
    void validateDiscountRate_ValidRate() {
        // given
        int discountAmount = 5000;
        int minimumOrderAmount = 5000;

        // when & then
        Assertions.assertThatThrownBy(() -> new DiscountAmount(discountAmount, minimumOrderAmount))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }
}
