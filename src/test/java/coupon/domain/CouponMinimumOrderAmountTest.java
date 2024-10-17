package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CouponMinimumOrderAmountTest {

    @DisplayName("최소 주문 금액이 5000원 이상 100000원 이하가 아니면 예외를 발생한다.")
    @ValueSource(longs = { 4999, 100001 })
    @ParameterizedTest
    void testValidateRange(Long minimumOrderAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponMinimumOrderAmount(minimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상, 100000원 이하여야 합니다.");
    }
}
