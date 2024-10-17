package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    @Test
    @DisplayName("최소 주문 금액은 5,000원 이상이어야 한다.")
    void minimumOrderAmount5000() {
        BigDecimal money = new BigDecimal(1000);

        assertThatThrownBy(() -> new MinimumOrderAmount(money))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최소 주문 금액은 100,000원 이하이어야 한다.")
    void minimumOrderAmount100000() {
        BigDecimal money = new BigDecimal(200000);

        assertThatThrownBy(() -> new MinimumOrderAmount(money))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
