package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderPriceTest {

    @DisplayName("최소 주문 금액을 검증한다.")
    @Test
    void validateOrderPrice() {
        assertAll(
                () -> assertThatThrownBy(() -> new OrderPrice(4_500L))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new OrderPrice(100_500L))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

}
