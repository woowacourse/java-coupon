package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    @DisplayName("할인 금액은 1,000원 이상 ~ 10,000원 이하여야 한다.")
    void validDiscountAmount(int amount) {
        DiscountAmount discountAmount = new DiscountAmount(amount);

        assertThat(discountAmount).isEqualTo(new DiscountAmount(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    @DisplayName("할인 금액이 1,000원 이상 ~ 10,000원 이하가 아니라면 예외가 발생한다.")
    void invalidDiscountAmount(int amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 1,000원 이상 ~ 10,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
    void validDiscountAmountUnit() {
        DiscountAmount discountAmount = new DiscountAmount(1500);

        assertThat(discountAmount).isEqualTo(new DiscountAmount(1500));
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아니라면 예외가 발생한다.")
    void invalidDiscountAmountUnit() {
        assertThatThrownBy(() -> new DiscountAmount(1100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 500원 단위어야 합니다.");
    }
}
