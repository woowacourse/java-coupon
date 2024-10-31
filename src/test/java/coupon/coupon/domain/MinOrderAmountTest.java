package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinOrderAmountTest {

    @Test
    @DisplayName("최소 주문 금액의 값을 알 수 있다.")
    void create() {
        MinOrderAmount minOrderAmount = new MinOrderAmount(1000);
        assertThat(minOrderAmount).isEqualTo(new MinOrderAmount(1000));
    }

    @Test
    @DisplayName("할인 금액의 값이 음수이면 예외가 발생한다.")
    void invalid() {
        assertThatThrownBy(() -> new MinOrderAmount(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
