package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountTest {

    @ParameterizedTest
    @DisplayName("할인 금액이 1,000원 미만이거나 10,000원 초과인 경우 예외를 발생한다.")
    @ValueSource(ints = {999, 10001})
    void validateDiscountAmountTest(int testDiscountAmount) {
        assertThatThrownBy(() -> new Discount(testDiscountAmount, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Discount Amount cannot be less than 1000 or more than 10000");
    }

    @Test
    @DisplayName("할인 금액이 500원의 배수가 아닌 경우 예외를 발생한다.")
    void validateDiscountMultipleTest() {
        int testDiscountAmount = 1501;
        assertThatThrownBy(() -> new Discount(testDiscountAmount, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Discount Amount must be multiple of 500");
    }

    @ParameterizedTest
    @DisplayName("최소 주문 금액이 5,000원 미만이거나 100,000원 초과인 경우 예외를 발생한다.")
    @ValueSource(ints = {4999, 100001})
    void validateMinOrderAmountTest(int testMinOrderAmount) {
        assertThatThrownBy(() -> new Discount(1000, testMinOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum Order Amount cannot be less than 5000 or more than 100000");
    }

    @ParameterizedTest
    @DisplayName("할인률이 3% 미만이거나 20% 초과인 경우 예외를 발생한다.")
    @CsvSource(value = {"1000, 35000", "10000, 45000"})
    void validateDiscountRateTest(int testDiscountAmount, int testMinOrderAmount) {
        assertThatThrownBy(() -> new Discount(testDiscountAmount, testMinOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Discount Rate cannot be less than 3% or more than 20%");
    }

}
