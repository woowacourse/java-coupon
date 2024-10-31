package coupon.domain.payment;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    @DisplayName("주문을 생성한다.")
    void create_payment() {
        // given
        final long amount = 10000L;
        final Category category = Category.FASHION;

        // when & then
        assertThatCode(() -> new Payment(amount, category))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 주문 금액은 5,000원 이상이어야 한다.")
    void minimum_payment_amount_should_greater_then_5000() {
        // given
        final long amount = 4999;
        final Category category = Category.FASHION;

        // when & then
        assertThatThrownBy(() -> new Payment(amount, category))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최소 주문 금액은 100,000원 이하여야 한다.")
    void maximum_payment_amount_should_not_be_exceed_100000() {
        // given
        final long amount = 100001;
        final Category category = Category.FASHION;

        // when & then
        assertThatThrownBy(() -> new Payment(amount, category))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
