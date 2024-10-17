package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.discountpolicy.DiscountRatePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountRatePolicyTest {

    @DisplayName("할인율은 할인금액 / 최소 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.")
    @Test
    void createDiscountRatePolicy() {
        // given
        int minimumOrderPrice = 30_000;
        int discountMoney = 1_000;
        int expected = 3;

        // when
        DiscountRatePolicy policy = new DiscountRatePolicy(discountMoney, minimumOrderPrice);
        int actual = policy.getDiscountRate();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("최소 주문 금액을 검증한다.")
    @Test
    void validateMinimumOrderPrice() {
        assertAll(
                () -> assertThatThrownBy(() -> new DiscountRatePolicy(1_000, 4_500))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new DiscountRatePolicy(1_000, 100_500))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("할인율을 검증한다.")
    @Test
    void validateMinimumDiscountRate() {
        assertThatThrownBy(() -> new DiscountRatePolicy(1_000, 50_000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
