package coupon.domain;

import static org.junit.jupiter.api.Assertions.*;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MinimumOrderAmountTest {

    @DisplayName("최소 주문 금액을 생성한다.")
    @Test
    void create() {
        MinimumOrderAmount amount = new MinimumOrderAmount(10000);
        assertEquals(10000, amount.getMinimumOrderAmount());
    }

    @DisplayName("최소 주문 금액은 5000원 이상이어야 한다.")
    @Test
    void validateMinimumOrderAmount() {
        assertThrows(CouponException.class, () -> new MinimumOrderAmount(4999));
    }

    @DisplayName("최소 주문 금액은 100000원 이하여야 한다.")
    @Test
    void validateMinimumOrderAmount2() {
        assertThrows(CouponException.class, () -> new MinimumOrderAmount(100001));
    }
}
