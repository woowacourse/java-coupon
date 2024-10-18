package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static coupon.coupon.domain.CouponDiscountAmount.INCREMENT_UNIT;
import static coupon.coupon.domain.CouponDiscountAmount.MAX_DISCOUNT_AMOUNT;
import static coupon.coupon.domain.CouponDiscountAmount.MIN_DISCOUNT_AMOUNT;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CouponDiscountAmountTest {

    static Stream<Long> testDataForValidateRange() {
        return Stream.of(MIN_DISCOUNT_AMOUNT - 1, MAX_DISCOUNT_AMOUNT + 1);
    }

    static Stream<Long> testDataForValidateIncrement() {
        return Stream.of(MIN_DISCOUNT_AMOUNT + INCREMENT_UNIT - 1, MAX_DISCOUNT_AMOUNT - INCREMENT_UNIT + 1);
    }

    @DisplayName("할인 금액이 일정 금액 범위에 해당하지 않으면, 예외를 발생한다.")
    @MethodSource("testDataForValidateRange")
    @ParameterizedTest
    void testValidateRange(Long discountAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + MIN_DISCOUNT_AMOUNT + "원 이상, " + MAX_DISCOUNT_AMOUNT + "원 이하여야 합니다.");
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
