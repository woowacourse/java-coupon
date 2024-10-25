package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    @DisplayName("최소 주문 금액은 5,000원 이상이어야 한다.")
    @Test
    void createMinimumOrderAmountWithLowerThanMinimum() {
        BigDecimal given = new BigDecimal(4999);

        assertThatThrownBy(() -> new MinimumOrderAmount(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_MINIMUM_ORDER_AMOUNT_INVALID.getMessage());
    }

    @DisplayName("최소 주문 금액은 100,000원 이하여야 한다.")
    @Test
    void createMinimumOrderAmountWithGreaterThanMaximum() {
        BigDecimal given = new BigDecimal(100001);

        assertThatThrownBy(() -> new MinimumOrderAmount(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_MINIMUM_ORDER_AMOUNT_INVALID.getMessage());
    }
}
