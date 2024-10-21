package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinOrderAmountTest {

    @Test
    void 최소_주문_금액_객체를_생성한다() {
        // given
        int minOrderAmount = 5_000;

        // when
        MinOrderAmount minOrderAmountObject = new MinOrderAmount(minOrderAmount);

        // then
        assertThat(minOrderAmountObject.getMinOrderAmount()).isEqualTo(minOrderAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {4_999, 100_001})
    void 최소_주문_금액이_범위를_벗어나면_IllegalArgumentException이_발생한다(int minOrderAmount) {
        // when & then
        assertThatThrownBy(() -> new MinOrderAmount(minOrderAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
    }
}
