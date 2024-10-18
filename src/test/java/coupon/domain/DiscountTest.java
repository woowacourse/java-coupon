package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountTest {

    @Test
    @DisplayName("할인 금액을 생성한다.")
    void create_discount() {
        // given
        final long price = 1000;

        // when & then
        assertThatCode(() -> new Discount(price))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("할인금액은 1,000원 이상이어야 한다.")
    void discount_amount_should_be_great_equal_then_1000_won() {
        // given
        final long price = 999;

        // when & then
        assertThatThrownBy(() -> new Discount(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인금액은 10,000원 이상이어야 한다.")
    void discount_amount_should_be_less_equal_then_10000_won() {
        // given
        final long price = 10500;

        // when & then
        assertThatThrownBy(() -> new Discount(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인 금액은 500원 단위여야 한다.")
    void discount_amount_should_divide_by_500() {
        // given
        final long price = 1501;

        // when
        assertThatThrownBy(() -> new Discount(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
