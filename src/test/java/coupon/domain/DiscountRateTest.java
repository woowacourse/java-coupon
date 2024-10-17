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
        int minimumOrderPrice = 30_000;
        int discountMoney = 1_000;
        int expected = 3;

        // when
        DiscountRate policy = new DiscountRate(discountMoney, minimumOrderPrice);
        int actual = policy.getDiscountRate();

        // then
        assertThat(actual).isEqualTo(expected);
    }


    @DisplayName("할인율을 검증한다.")
    @Test
    void validateMinimumDiscountRate() {
        assertThatThrownBy(() -> new DiscountRate(1_000, 50_000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
