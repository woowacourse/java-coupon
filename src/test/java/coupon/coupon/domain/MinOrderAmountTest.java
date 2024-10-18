package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinOrderAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {5000, 100000})
    @DisplayName("최소 주문 금액은 5,000원 이상 ~ 100,000원 이하이어야 한다.")
    void validOrderAmount(int amount) {
        MinOrderAmount minOrderAmount = new MinOrderAmount(amount);

        assertThat(minOrderAmount).isEqualTo(new MinOrderAmount(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    @DisplayName("최소 주문 금액은 5,000원 이상 ~ 100,000원 이하이어야 한다.")
    void invalidOrderAmount(int amount) {
        assertThatThrownBy(() -> new MinOrderAmount(amount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
