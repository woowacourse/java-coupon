package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.coupon.DiscountRange;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountRangeTest {

    @ParameterizedTest
    @MethodSource("getDiscountAmountAndMinOrderAmount")
    @DisplayName("정해진 할인율 범위를 넘어가면 예외 발생")
    void givenDiscountRateIsExceedRange(long discountAmount, long minOrderAmount) {
        assertThatCode(() -> new DiscountRange(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("정해진 할인율 범위를 벗어났습니다.");
    }

    private static Stream<Arguments> getDiscountAmountAndMinOrderAmount() {
        return Stream.of(
                Arguments.of(1000, 50000),
                Arguments.of(21000, 100000));
    }
}
