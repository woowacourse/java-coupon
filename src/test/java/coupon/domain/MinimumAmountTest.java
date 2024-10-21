package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MinimumAmountTest {

    @ParameterizedTest
    @DisplayName("최소 금액을 생성한다.")
    @CsvSource(value = {"5_000", "10_000", "100_000"})
    void createMinimumAmount(int value) {
        BigDecimal amount = BigDecimal.valueOf(value);

        MinimumAmount minimumAmount = new MinimumAmount(amount);

        assertThat(minimumAmount).isEqualTo(new MinimumAmount(BigDecimal.valueOf(value)));
    }

    @Test
    @DisplayName("최소 금액이 null인 경우 예외가 발생한다.")
    void createMinimumAmountWhenNull() {
        BigDecimal nullAmount = null;

        assertThatThrownBy(() -> new MinimumAmount(nullAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 필수입니다.");
    }

    @Test
    @DisplayName("최소 금액이 5,000원 미만인 경우 예외가 발생한다.")
    void createMinimumAmountWhenAmountLessThan5_000() {
        BigDecimal tooSmallAmount = BigDecimal.valueOf(4_999);

        assertThatThrownBy(() -> new MinimumAmount(tooSmallAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최소 금액이 100,000원 초과인 경우 예외가 발생한다.")
    void createMinimumAmountWhenAmountExceeds100_000() {
        BigDecimal tooLargeAmount = BigDecimal.valueOf(100_001);

        assertThatThrownBy(() -> new MinimumAmount(tooLargeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 100,000원 이하여야 합니다.");
    }
}
