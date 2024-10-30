package coupon.coupon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponValidatorTest {

    private CouponValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CouponValidator();
    }

    @ParameterizedTest
    @CsvSource(value = {"1000, 30000", "10000, 100000"})
    @DisplayName("할인 금액은 1,000원 이상 ~ 10,000원 이하여야 하고, 할인율이 3% 이상 ~ 20% 이하여야 한다.")
    void validDiscountAmount(int discountAmount, int minOrderAmount) {
        assertThatCode(() -> validator.validateAmount(discountAmount, minOrderAmount))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    @DisplayName("할인 금액이 1,000원 이상 ~ 10,000원 이하가 아니라면 예외가 발생한다.")
    void invalidDiscountAmount(int discountAmount) {
        assertThatThrownBy(() -> validator.validateAmount(discountAmount, 5000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 1,000원 이상 ~ 10,000원 이하여야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1000, 50000", "1000, 4700"})
    @DisplayName("할인율이 3% 이상 ~ 20%이하가 아니라면 예외가 발생한다.")
    void invalidDiscountRate(int discountAmount, int minOrderAmount) {
        assertThatThrownBy(() -> validator.validateAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인율은 3% 이상 ~ 20% 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
    void validDiscountAmountUnit() {
        assertThatCode(() -> validator.validateAmount(1500, 10_000))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아니라면 예외가 발생한다.")
    void invalidDiscountAmountUnit() {
        assertThatThrownBy(() -> validator.validateAmount(1100, 10_000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 500원 단위어야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1000, 5000", "10000, 100000"})
    @DisplayName("최소 주문 금액은 5,000원 이상 ~ 100,000원 이하이어야 한다.")
    void validOrderAmount(int discountAmount, int minOrderAmount) {
        assertThatCode(() -> validator.validateAmount(discountAmount, minOrderAmount))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"1000, 4999", "10000, 100001"})
    @DisplayName("최소 주문 금액은 5,000원 이상 ~ 100,000원 이하이어야 한다.")
    void invalidOrderAmount(int discountAmount, int minOrderAmount) {
        assertThatThrownBy(() -> validator.validateAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최소 주문 금액은 5,000원 이상 ~ 100,000원 이하여야 합니다.");
    }
}
