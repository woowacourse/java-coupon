package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinOrderAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    void 최소_주문_금액이_5000원_이상_100000원_이하가_아니면_예외가_발생한다(int amount) {
        assertThatThrownBy(() -> new MinOrderAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
    }

    @Test
    void 최소_주문_금액이_5000원_이상_10000원_이하이면_객체가_생성된다() {
        assertThatCode(() -> new MinOrderAmount(5000))
                .doesNotThrowAnyException();
    }
}
