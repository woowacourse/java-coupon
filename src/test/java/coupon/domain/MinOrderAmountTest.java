package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.coupon.MinOrderAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinOrderAmountTest {

    @ParameterizedTest
    @ValueSource(longs = {4999, 100001})
    @DisplayName("정해진 최소 주문 금액 범위를 벗어나면 예외 발생")
    void givenMinOrderAmountIsExceedRange(long minOrderAmount) {
        assertThatCode(() -> new MinOrderAmount(minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("정해진 최소 주문 금액 범위를 벗어났습니다.");
    }
}
