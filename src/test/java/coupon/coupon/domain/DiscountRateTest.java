package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountRateTest {

    @Test
    @DisplayName("할인율 생성 성공: 할인금액과 최소 주문 금액을 입력받아 할인율을 생성, 소수점은 버림")
    void createRate() {
        final Long discountAmount = 1000L;
        final Long minimumOrderAmount = 30000L;
        assertThat(DiscountRate.from(discountAmount, minimumOrderAmount).discountRate()).isEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 21})
    @DisplayName("할인율 생성 실패: 3% 미만 또는 20% 초과일 경우 예외 발생")
    void createRateWhenInvalidRange(final Integer rate) {
        assertThatThrownBy(() -> new DiscountRate(rate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하의 값이어야 합니다.");
    }
}
