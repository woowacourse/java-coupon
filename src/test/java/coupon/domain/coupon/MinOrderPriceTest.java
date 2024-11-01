package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import coupon.domain.coupon.MinOrderPrice;

class MinOrderPriceTest {

    @DisplayName("최소 주문 금액이 5,000원 이상 100,000원 이하이면 생성에 성공한다.")
    @ParameterizedTest
    @ValueSource(ints = {5000, 100000})
    void createMinOrderPrice(int price) {
        assertThatCode(() -> new MinOrderPrice(price)).doesNotThrowAnyException();
    }

    @DisplayName("최소 주문 금액이 5,000원 미만 100,000원 초과이면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    void createMinOrderPriceFail(int price) {
        assertThatThrownBy(() -> new MinOrderPrice(price)).isInstanceOf(IllegalArgumentException.class);
    }
}
