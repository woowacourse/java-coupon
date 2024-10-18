package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponDiscountAmountTest {

    @DisplayName("유효한 할인 금액이 입력되면 객체를 생성한다.")
    @Test
    void createObject() {
        // Given
        final int discountAmount = 3000;
        final CouponMinimumOrderAmount couponMinimumOrderAmount = new CouponMinimumOrderAmount(30000);

        // When
        final CouponDiscountAmount couponDiscountAmount = new CouponDiscountAmount(discountAmount,
                couponMinimumOrderAmount);

        // Then
        assertThat(couponDiscountAmount).isNotNull();
    }

    @DisplayName("유효하지 않은 범위의 할인 금액이 입력되면 예외를 발생시킨다.")
    @ValueSource(ints = {999, 10001})
    @ParameterizedTest
    void validateDiscountAmountLimit(final int input) {
        // Given
        final CouponMinimumOrderAmount couponMinimumOrderAmount = new CouponMinimumOrderAmount(10000);

        // When & Then
        assertThatThrownBy(() -> new CouponDiscountAmount(input, couponMinimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1000원 이상, 10000원 이하여햐 합니다. - " + input);
    }

    @DisplayName("유효하지 않은 단위의 할인 금액이 입력되면 예외를 발생시킨다.")
    @Test
    void validateDiscountAmountUnit() {
        // Given
        final int discountAmount = 1300;
        final CouponMinimumOrderAmount couponMinimumOrderAmount = new CouponMinimumOrderAmount(30000);

        // When & Then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount, couponMinimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다. - " + discountAmount);
    }

    @DisplayName("유효하지 않은 할인율의 할인 금액이 입력되면 예외를 발생시킨다.")
    @Test
    void validateDiscountPercentage() {
        // Given
        final int discountAmount = 1000;
        final CouponMinimumOrderAmount couponMinimumOrderAmount = new CouponMinimumOrderAmount(50000);
        final int discountPercentage = 2;

        // When & Then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount, couponMinimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상, 20% 이하만 가능합니다. - " + discountPercentage);
    }
}
