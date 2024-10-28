package coupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import coupon.domain.MinimumOrderAmount;
import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @DisplayName("할인 금액을 생성한다.")
    @Test
    void create() {
        DiscountAmount amount = new DiscountAmount(1000, new MinimumOrderAmount(10000));
        assertEquals(1000, amount.getDiscountAmount());
    }

    @DisplayName("할인 금액은 1000원 이상이어야 한다.")
    @Test
    void validateDiscountAmount() {
        assertThrows(CouponException.class, () -> new DiscountAmount(950, new MinimumOrderAmount(10000)));
    }

    @DisplayName("할인 금액은 10000원 이하여야 한다.")
    @Test
    void validateDiscountAmount2() {
        assertThrows(CouponException.class, () -> new DiscountAmount(10500, new MinimumOrderAmount(10000)));
    }

    @DisplayName("할인 금액은 500원 단위로 입력해야 한다.")
    @Test
    void validateDiscountAmount3() {
        assertThrows(CouponException.class, () -> new DiscountAmount(1001, new MinimumOrderAmount(10000)));
    }

    @DisplayName("할인율은 3% 이상이어야 한다.")
    @Test
    void validateDiscountRate() {
        assertThrows(CouponException.class, () -> new DiscountAmount(1000, new MinimumOrderAmount(35000)));
    }

    @DisplayName("할인율은 20% 이하여야 한다.")
    @Test
    void validateDiscountRate2() {
        assertThrows(CouponException.class, () -> new DiscountAmount(2500, new MinimumOrderAmount(10000)));
    }
}
