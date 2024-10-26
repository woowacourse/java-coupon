package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderAmountTest {

    @ParameterizedTest
    @ValueSource(longs = { 4_999, 100_001 })
    void createAmountWhenInvalidRange(final Long invalidAmount) {
        assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하이어야 합니다.");
    }
}
