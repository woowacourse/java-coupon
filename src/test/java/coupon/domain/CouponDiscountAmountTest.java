package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CouponDiscountAmountTest {
    private static final Long MIN_AMOUNT = 1000L;
    private static final Long MAX_AMOUNT = 10000L;
    private static final Long INCREMENT_UNIT = 500L;

    static Stream<Long> testDataForValidateRange() {
        return Stream.of(MIN_AMOUNT - 1, MAX_AMOUNT + 1);
    }

    static Stream<Long> testDataForValidateIncrement() {
        return Stream.of(MIN_AMOUNT + INCREMENT_UNIT - 1, MAX_AMOUNT - INCREMENT_UNIT + 1);
    }

    @DisplayName("할인 금액이 일정 금액 범위에 해당하지 않으면, 예외를 발생한다.")
    @MethodSource("testDataForValidateRange")
    @ParameterizedTest
    void testValidateRange(Long discountAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + MIN_AMOUNT + "원 이상, " + MAX_AMOUNT + "원 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 일정 단위로 설정되지 않았다면 예외를 발생한다.")
    @MethodSource("testDataForValidateIncrement")
    @ParameterizedTest
    void testValidateIncrement(Long discountAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + INCREMENT_UNIT + "원 단위로 설정되어야 합니다.");
    }
}
