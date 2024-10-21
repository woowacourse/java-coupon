package coupon.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {1_020, 9_877})
    @DisplayName("쿠폰 할인금액은 500원 단위다.")
    void validateDiscountAmountUnit(int invalidDiscountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(invalidDiscountAmount))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_UNIT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 10_500})
    @DisplayName("쿠폰 할인금액은 1,000원 이상 10,000원 이하다.")
    void validateDiscountAmountRange(int invalidDiscountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(invalidDiscountAmount))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_RANGE.getMessage());
    }
}
