package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @DisplayName("할인 금액이 정해진 할인 금액 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {DiscountAmount.MIN_DISCOUNT_AMOUNT - 1, DiscountAmount.MAX_DISCOUNT_AMOUNT + 1})
    void invalidOrderPrice(int discountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액이 정해진 할인 금액 범위를 벗어나지 않으면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {DiscountAmount.MIN_DISCOUNT_AMOUNT, DiscountAmount.MAX_DISCOUNT_AMOUNT})
    void validOrderPrice(int discountAmount) {
        assertThatCode(() -> new DiscountAmount(discountAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인 금액이 정해진 할인 금액 단위로 나누어지지 않으면 예외가 발생한다.")
    @Test
    void invalidDiscountAmountUnit() {
        assertThatCode(() -> new DiscountAmount((DiscountAmount.DISCOUNT_AMOUNT_UNIT * 2) + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액이 정해진 할인 금액 단위로 나누어지면 예외가 발생하지 않는다.")
    @Test
    void validDiscountAmountUnit() {
        assertThatCode(() -> new DiscountAmount(DiscountAmount.DISCOUNT_AMOUNT_UNIT * 2))
                .doesNotThrowAnyException();
    }
}
