package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderAmountTest {

    @ParameterizedTest
    @ValueSource(ints = { 4_999, 100_001 })
    void createAmountWhenInvalidRange(final int invalidAmount) {
        assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 10,000원 이하이어야 합니다.");
    }
}
