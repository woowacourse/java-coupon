package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @DisplayName("할인금액 객체가 정상적으로 생성됩니다.")
    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    void create_discount_amount(int amount) {
        assertThatCode(
                () -> new DiscountAmount(amount)
        ).doesNotThrowAnyException();
    }

    @DisplayName("할인금액이 1000원 미만일 경우 예외가 발생한다")
    @Test
    void throw_exception_when_discount_amount_is_lower_than_1000() {
        assertThatThrownBy(() -> new DiscountAmount(500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원단위의 1000원이상, 10000원이하로 해야합니다.");
    }

    @DisplayName("할인금액이 10,000원 초과일 경우 예외가 발생한다")
    @Test
    void throw_exception_when_discount_amount_is_higher_than_10000() {
        assertThatThrownBy(() -> new DiscountAmount(10_500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원단위의 1000원이상, 10000원이하로 해야합니다.");
    }

    @DisplayName("할인 금액이 500원 단위가 아닐 경우 예외가 발생한다")
    @Test
    void throw_exception_when_discount_amount_unit_is_not_500_unit() {
        assertThatThrownBy(() -> new DiscountAmount(500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원단위의 1000원이상, 10000원이하로 해야합니다.");
    }
}
