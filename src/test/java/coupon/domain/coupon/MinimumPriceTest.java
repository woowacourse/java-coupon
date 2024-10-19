package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinimumPriceTest {

    @Test
    @DisplayName("가능한 범위의 최소값이 아닌 최소금액인 경우 에러를 발생한다.")
    void minimumPrice_WhenSmall() {
        assertThatThrownBy(() -> new MinimumPrice(MinimumPrice.MINIMUM_PRICE - 1))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.DISCOUNT_PRICE_NOT_IN_RANGE.getMessage());
    }

    @Test
    @DisplayName("가능한 범위의 최대값이 아닌 최소금액인 경우 에러를 발생한다.")
    void minimumPrice_WhenBig() {
        assertThatThrownBy(() -> new MinimumPrice(MinimumPrice.MAXIMUM_PRICE + 1))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.DISCOUNT_PRICE_NOT_IN_RANGE.getMessage());
    }
}
