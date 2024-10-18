package coupon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    @DisplayName("금액이 큰지 비교해서 참 거짓을 반환한다.")
    void greater_than_case() {
        final Money money = new Money(new BigDecimal(1500));
        assertThat(money.isGreaterOrEqualThan(new BigDecimal(1000))).isTrue();
        assertThat(money.isGreaterOrEqualThan(new BigDecimal(1500))).isTrue();
        assertThat(money.isGreaterThan(new BigDecimal(1500))).isFalse();
    }

    @Test
    @DisplayName("금액이 작은지 비교해서 참 거짓을 반환한다.")
    void less_than_case() {
        final Money money = new Money(new BigDecimal(10_000));
        assertThat(money.isLessOrEqualThan(new BigDecimal(12_000))).isTrue();
        assertThat(money.isLessOrEqualThan(new BigDecimal(10_000))).isTrue();
        assertThat(money.isLessThan(new BigDecimal(10_000))).isFalse();
    }
}
