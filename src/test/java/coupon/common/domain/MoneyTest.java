package coupon.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    @DisplayName("금액을 더할 수 있다.")
    void testPlus() {
        // given
        Money money1 = Money.wons(1000);
        Money money2 = Money.wons(500);

        // when
        Money result = money1.plus(money2);

        // then
        assertThat(result).isEqualTo(Money.wons(1500));
    }

    @Test
    @DisplayName("금액을 뺄 수 있다.")
    void testMinus() {
        // given
        Money money1 = Money.wons(1000);
        Money money2 = Money.wons(300);

        // when
        Money result = money1.minus(money2);

        assertThat(result).isEqualTo(Money.wons(700));
    }

    @Test
    @DisplayName("금액을 곱할 수 있다.")
    void testTimes() {
        // given
        Money money = Money.wons(2000);

        // when
        Money result = money.times(0.5);

        // then
        assertThat(result).isEqualTo(Money.wons(1000));
    }

    @Test
    @DisplayName("금액과 다른 금액의 비율을 구할 수 있다.")
    void testOfRatio() {
        // given
        Money total = Money.wons(30000);
        Money part = Money.wons(1000);

        // when
        int ratio = part.ofRatio(total);

        // then
        assertThat(ratio).isEqualTo(3); // 1000 / 30000 * 100 = 3.33 -> 3
    }

    @Test
    @DisplayName("금액이 다른 금액의 배수인지 확인할 수 있다.")
    void testIsMultipleOf() {
        // given
        Money money1 = Money.wons(1500);
        Money money2 = Money.wons(500);

        // when & then
        assertAll(
                () -> assertThat(money1.isMultipleOf(money2)).isTrue(),
                () -> assertThat(money2.isMultipleOf(money1)).isFalse()
        );
    }

    @Test
    @DisplayName("금액이 작은지 비교할 수 있다.")
    void testIsLessThan() {
        // given
        Money money1 = Money.wons(1000);
        Money money2 = Money.wons(2000);

        // when & then
        assertAll(
                () -> assertTrue(money1.isLessThan(money2)),
                () -> assertFalse(money2.isLessThan(money1))
        );
    }

    @Test
    @DisplayName("금액이 같은지 크거나 같은지 비교할 수 있다.")
    void testIsGreaterThanOrEqual() {
        // given
        Money money1 = Money.wons(1000);
        Money money2 = Money.wons(2000);

        // when & then
        assertAll(
                () -> assertThat(money2.isGreaterThanOrEqual(money1)).isTrue(),
                () -> assertThat(money1.isGreaterThanOrEqual(money2)).isFalse(),
                () -> assertThat(money1.isGreaterThanOrEqual(money1)).isTrue()
        );
    }
}
