package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRateTest {

    @DisplayName("할인율은 할인금액 / 최소 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.")
    @Test
    void createDiscountRatePolicy() {
        // given
        Long minimumOrderPrice = 30_000L;
        Long discountMoney = 1_000L;
        Long expected = 3L;

        // when
        DiscountRate policy = new DiscountRate(discountMoney, minimumOrderPrice);
        Long actual = policy.getDiscountRate();

        // then
        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("할인율을 검증한다.")
    @Test
    void validateMinimumDiscountRate() {
        assertThatThrownBy(() -> new DiscountRate(1_000L, 50_000L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
