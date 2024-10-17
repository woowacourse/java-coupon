package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CouponDiscountAmountTest {

    @DisplayName("할인 금액이 1000원 이상 10000원 이하가 아니면 예외를 발생한다.")
    @ValueSource(longs = {999, 10001})
    @ParameterizedTest
    void testValidateRange(Long discountAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1000원 이상, 10000원 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 500원 단위로 설정되지 않았다면 예외를 발생한다.")
    @ValueSource(longs = {1499, 1501})
    @ParameterizedTest
    void testValidateIncrement(Long discountAmount) {
        // given & when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정되어야 합니다.");
    }
}
