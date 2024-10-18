package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiscountRateTest {

    @Test
    void 할인율_객체를_생성한다() {
        // given
        int discountAmount = 1_000;
        int baseAmount = 30_000;

        // when
        DiscountRate discountRate = DiscountRate.calculateDiscountRate(discountAmount, baseAmount);

        // then
        assertThat(discountRate.getDiscountRate()).isEqualTo(3);
    }

    @Test
    void 기준_금액이_0이면_IllegalArgumentException이_발생한다() {
        // given
        int discountAmount = 1_000;
        int baseAmount = 0;

        // when & then
        assertThatThrownBy(() -> DiscountRate.calculateDiscountRate(discountAmount, baseAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("기준 금액은 0원 이상이어야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1_000, 50_000", "21_000, 100_000"})
    void 할인율이_범위를_벗어나면_IllegalArgumentException이_발생한다(int discountAmount, int baseAmount) {
        // when & then
        assertThatThrownBy(() -> DiscountRate.calculateDiscountRate(discountAmount, baseAmount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }
}
