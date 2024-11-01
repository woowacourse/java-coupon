package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import coupon.coupon.domain.CouponMinimumOrderAmount;

class CouponMinimumOrderAmountTest {

    @DisplayName("유효한 최소 주문 금액이 입력되면 객체를 생성한다.")
    @Test
    void createObject() {
        // Given
        final int input = 6000;

        // When
        final CouponMinimumOrderAmount couponMinimumOrderAmount = new CouponMinimumOrderAmount(input);

        // Then
        assertThat(couponMinimumOrderAmount).isNotNull();
    }

    @DisplayName("유효하지 않은 범위의 최소 주문 금액이 입력되면 예외를 발생시킨다.")
    @ValueSource(ints = {4999, 100001})
    @ParameterizedTest
    void validateMinimumOrderAmountLimit(final int input) {
        // When & Then
        assertThatThrownBy(() -> new CouponMinimumOrderAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상, 100000원 이하만 가능합니다. - " + input);
    }
}
