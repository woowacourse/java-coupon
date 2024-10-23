package coupon.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderPriceTest {

    @ParameterizedTest
    @ValueSource(ints = {500, 110_000})
    @DisplayName("쿠폰의 최소 주문 금액은 5,000원 이상 100,000원 이하다.")
    void validateMinimumOrderPrice(int invalidMinimumOrderPrice) {
        assertThatThrownBy(() -> new MinimumOrderPrice(invalidMinimumOrderPrice))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_MINIMUM_ORDER_PRICE_RANGE.getMessage());
    }
}
