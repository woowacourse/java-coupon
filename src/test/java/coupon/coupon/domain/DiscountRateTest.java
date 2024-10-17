package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiscountRateTest {

    @DisplayName("할인율이 3보다 작거나 20보다 크면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"200, 10000", "2100, 10000"})
    void invalidDiscountRate(int discountAmount, int minOrderPrice) { // 할인율 = (할인금액 / 최소주문금액) * 100
        assertThatThrownBy(() -> new DiscountRate(discountAmount, minOrderPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인율이 3이상, 20이하이면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @CsvSource({"300, 10000", "2000, 10000"})
    void validDiscountRate(int discountAmount, int minOrderPrice) { // 할인율 = (할인금액 / 최소주문금액) * 100
        assertThatCode(() -> new DiscountRate(discountAmount, minOrderPrice))
                .doesNotThrowAnyException();
    }
}
