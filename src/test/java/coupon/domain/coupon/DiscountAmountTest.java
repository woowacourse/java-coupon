package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @Test
    @DisplayName("할인 금액은 1000원 이상이어야 한다.")
    void discountAmount1000() {
        BigDecimal money = new BigDecimal(500);

        assertThatThrownBy(() -> new DiscountAmount(money))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인 금액은 10000원 이하이어야 한다.")
    void discountAmount10000() {
        BigDecimal money = new BigDecimal(100000);

        assertThatThrownBy(() -> new DiscountAmount(money))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인 금액은 500원 단위여야 한다.")
    void discountAmount500Unit() {
        BigDecimal money = new BigDecimal(1200);

        assertThatThrownBy(() -> new DiscountAmount(money))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인률은 3% 이상이어야 한다.")
    void discountAmount3Rate() {
        BigDecimal discountMoney = new BigDecimal(1000);
        BigDecimal minimumOrderAmount = new BigDecimal(50000);
        DiscountAmount discountAmount = new DiscountAmount(discountMoney);

        assertFalse(discountAmount.isValidDiscountRate(minimumOrderAmount));
    }

    @Test
    @DisplayName("할인률은 20% 이하이어야 한다.")
    void discountAmount20Rate() {
        BigDecimal discountMoney = new BigDecimal(1000);
        BigDecimal minimumOrderAmount = new BigDecimal(1000);
        DiscountAmount discountAmount = new DiscountAmount(discountMoney);

        assertFalse(discountAmount.isValidDiscountRate(minimumOrderAmount));
    }
}
