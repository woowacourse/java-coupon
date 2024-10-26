package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderAmountTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("최소 주문 금액 생성 실패: 최소 주문 금액이 Null 인 경우")
    void createName(Long minimumOrderAmount) {
        assertThatThrownBy(() -> new MinimumOrderAmount(minimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 null이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = { 4_999, 100_001 })
    @DisplayName("최소 주문 금액 생성 실패: 최소 주문 금액이 범위에 맞지 않는 경우")
    void createAmountWhenInvalidRange(final Long invalidAmount) {
        assertThatThrownBy(() -> new MinimumOrderAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하이어야 합니다.");
    }
}
