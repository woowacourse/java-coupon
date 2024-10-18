package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountRateTest {

    @ParameterizedTest
    @ValueSource(doubles = {2.9, 20.1})
    void createRateWhenInvalidRange(final double rate) {
        assertThatThrownBy(() -> new DiscountRate(rate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하의 값이어야 합니다.");
    }
}
