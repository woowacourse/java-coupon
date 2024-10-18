package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRateCalculatorTest {

    @Test
    @DisplayName("할인 금액은  할인금액 / 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.")
    void floor_by_discount_mount_with_min_order_price() {
        // given
        final long orderPrice = 30001;
        final long discountAmount = 1500;

        // when
        final DiscountRateCalculator sut = new DiscountRateCalculator(orderPrice, discountAmount);

        // then
        assertThat(sut.calculate()).isEqualTo(20L);
    }

    @Test
    @DisplayName("할인율은 3% 이상이어야 한다.")
    void discount_rate_should_greater_equal_then_3() {
        // given
        final long orderPrice = 4499;
        final long discountAmount = 1500;
        final DiscountRateCalculator calculator = new DiscountRateCalculator(orderPrice, discountAmount);

        // when & then
        assertThatThrownBy(calculator::validateRate)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인율은 20% 이하여야 한다.")
    void discount_rate_should_less_equal_then_20() {
        // given
        final long orderPrice = 31500;
        final long discountAmount = 1500;
        final DiscountRateCalculator calculator = new DiscountRateCalculator(orderPrice, discountAmount);

        // when & then
        assertThatThrownBy(calculator::validateRate)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
