package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @Test
    void 할인_금액_객체를_생성한다() {
        // given
        int amount = 1_000;

        // when
        DiscountAmount discountAmount = new DiscountAmount(amount);

        // then
        assertThat(discountAmount.getDiscountAmount()).isEqualTo(amount);
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 10_001})
    void 할인_금액이_범위를_벗어나면_IllegalArgumentException이_발생한다(int amount) {
        // when & then
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
    }

    @Test
    void 할인_금액이_500원_단위가_아니면_IllegalArgumentException이_발생한다() {
        // given
        int amount = 1_100;

        // when & then
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위만 가능합니다.");
    }
}
