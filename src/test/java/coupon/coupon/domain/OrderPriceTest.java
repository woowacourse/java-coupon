package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderPriceTest {

    @DisplayName("주문 금액이 정해진 주문 금액 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {OrderPrice.MIN_ORDER_PRICE - 1, OrderPrice.MAX_ORDER_PRICE + 1})
    void invalidOrderPrice(int price) {
        assertThatThrownBy(() -> new OrderPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 금액이 정해진 주문 금액 범위를 안이라면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {OrderPrice.MIN_ORDER_PRICE, OrderPrice.MAX_ORDER_PRICE})
    void validOrderPrice(int price) {
        assertThatCode(() -> new OrderPrice(price))
                .doesNotThrowAnyException();
    }
}
