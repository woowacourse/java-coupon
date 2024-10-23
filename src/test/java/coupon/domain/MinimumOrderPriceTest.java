package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderPriceTest {

    @DisplayName("최소 주문 금액 객체를 생성할 수 있습니다.")
    @ParameterizedTest
    @ValueSource(ints = {5000, 100000})
    void create_minimum_order_price(int price) {
        assertThatCode(() -> new MinimumOrderPrice(price))
                .doesNotThrowAnyException();
    }

    @DisplayName("최소 주문 금액이 5000원 미만이면 예외가 발생한다.")
    @Test
    void throw_exception_when_minimum_order_price_is_lower_than_5000() {
        assertThatThrownBy(() -> new MinimumOrderPrice(4900))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000이상 100000이하여야합니다.");
    }

    @DisplayName("최소 주문 금액이 100_000원 초과하면 예외가 발생한다.")
    @Test
    void throw_exception_when_minimum_order_price_is_higher_than_100_000() {
        assertThatThrownBy(() -> new MinimumOrderPrice(4900))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000이상 100000이하여야합니다.");
    }
}
