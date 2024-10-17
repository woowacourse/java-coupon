package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
    @Test
    void createDiscountAmountWithLowerThanMinimum() {
        BigDecimal given = new BigDecimal(500);

        assertThatThrownBy(() -> new DiscountAmount(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DISCOUNT_AMOUNT_INVALID.getMessage());
    }

    @DisplayName("할인 금액은 10,000원 이하여야 한다.")
    @Test
    void createDiscountAmountWithGreaterThanMaximum() {
        BigDecimal given = new BigDecimal(10500);

        assertThatThrownBy(() -> new DiscountAmount(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DISCOUNT_AMOUNT_INVALID.getMessage());
    }

    @DisplayName("할인 금액은 500원 단위여야 한다.")
    @Test
    void createDiscountAmountWithNotValidUnit() {
        BigDecimal given = new BigDecimal(1100);

        assertThatThrownBy(() -> new DiscountAmount(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DISCOUNT_UNIT_MISMATCH.getMessage());
    }
}
