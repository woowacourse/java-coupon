package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    @DisplayName("할인 금액이 잘 생성된다.")
    void discountAmount(int amount) {
        assertDoesNotThrow(() -> new DiscountAmount(amount, amount * 10));
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    @DisplayName("할인 금액이 1000원 미만이거나 10000원 초과일 경우 예외가 발생한다.")
    void validateRange(int amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount, amount * 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 500원 단위로 설정되지 않을 경우 예외가 발생한다.")
    void validateMultiple() {
        assertThatThrownBy(() -> new DiscountAmount(1200, 12000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @Test
    @DisplayName("할인율이 3% 미만일 경우 예외가 발생한다.")
    void validateDiscountRateLessThanThree() {
        assertThatThrownBy(() -> new DiscountAmount(2000, 100000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인율이 20% 초과일 경우 예외가 발생한다.")
    void validateDiscountRateMoreThanTwenty() {
        assertThatThrownBy(() -> new DiscountAmount(2500, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }
}
