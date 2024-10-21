package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiscountAmountTest {

    @ParameterizedTest
    @DisplayName("할인 금액을 생성한다.")
    @CsvSource(value = {"1_000", "5_000", "10_000"})
    void createDiscountAmount(int value) {
        BigDecimal amount = BigDecimal.valueOf(value);

        DiscountAmount discountAmount = new DiscountAmount(amount);

        assertThat(discountAmount).isEqualTo(new DiscountAmount(BigDecimal.valueOf(value)));
    }

    @Test
    @DisplayName("할인 금액이 null인 경우 예외가 발생한다.")
    void createDiscountAmountWhenNull() {
        BigDecimal nullAmount = null;

        assertThatThrownBy(() -> new DiscountAmount(nullAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 필수입니다.");
    }

    @Test
    @DisplayName("최소 금액이 1,000원 미만인 경우 예외가 발생한다.")
    void createDiscountAmountWhenAmountLessThan5_000() {
        BigDecimal tooSmallAmount = BigDecimal.valueOf(500);

        assertThatThrownBy(() -> new DiscountAmount(tooSmallAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최소 금액이 10,000원 초과인 경우 예외가 발생한다.")
    void createDiscountAmountWhenAmountExceeds100_000() {
        BigDecimal tooLargeAmount = BigDecimal.valueOf(10_500);

        assertThatThrownBy(() -> new DiscountAmount(tooLargeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 10,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아닌 경우 예외가 발생한다.")
    void createDiscountAmountWhenAmountIsNot500() {
        BigDecimal not500Amount = BigDecimal.valueOf(1_100);

        assertThatThrownBy(() -> new DiscountAmount(not500Amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위여야 합니다.");
    }
}
