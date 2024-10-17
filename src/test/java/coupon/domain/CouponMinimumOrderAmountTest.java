package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CouponMinimumOrderAmountTest {
    private static final Long MIN_AMOUNT = 5000L;
    private static final Long MAX_AMOUNT = 100000L;

    static Stream<Long> testDataForValidateRange() {
        return Stream.of(MIN_AMOUNT - 1, MAX_AMOUNT + 1);
    }

    @DisplayName("최소 주문 금액이 일정 금액 범위에 해당하지 않으면, 예외를 발생한다.")
    @MethodSource("testDataForValidateRange")
    @ParameterizedTest
    void testValidateRange(Long minimumOrderAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponMinimumOrderAmount(minimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 " + MIN_AMOUNT + "원 이상, " + MAX_AMOUNT + "원 이하여야 합니다.");
    }
}
