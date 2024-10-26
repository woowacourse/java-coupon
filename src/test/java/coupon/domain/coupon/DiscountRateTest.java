package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.DiscountRate;
import coupon.domain.coupon.MinOrderPrice;

class DiscountRateTest {

    @DisplayName("할인율이 3% 이상 20% 이하이면 생성에 성공한다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 20})
    void createDiscountPercent(int rate) {
        assertThatCode(() -> new DiscountRate(rate)).doesNotThrowAnyException();
    }

    @DisplayName("할인율이 3% 미만 20% 초과이면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(ints = {2, 21})
    void createDiscountPercentFail(int rate) {
        assertThatThrownBy(() -> new DiscountRate(rate)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인율은 (할인 금액 / 최소 주문 금액) 식을 사용하며, 소수점은 버린다")
    @Test
    void calculateRate() {
        // given
        DiscountAmount discountAmount = new DiscountAmount(1000);
        MinOrderPrice minOrderPrice = new MinOrderPrice(30000);

        // when
        DiscountRate discountRate = new DiscountRate(discountAmount, minOrderPrice);

        // then
        assertThat(discountRate.getDiscountRate()).isEqualTo(3);
    }
}
