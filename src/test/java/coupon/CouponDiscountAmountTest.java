package coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponDiscountAmountTest {

    @Test
    @DisplayName("쿠폰 할인 가격을 생성한다.")
    void create() {
        BigDecimal amount = new BigDecimal(1000);

        assertThatCode(() -> new CouponDiscountAmount(amount))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
    void minimumAmount() {
        BigDecimal amount = new BigDecimal("999.999");

        assertThatThrownBy(() -> new CouponDiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 최소 할인 금액은 1000원 이상입니다.");
    }

    @Test
    @DisplayName("할인 금액은 10,000원 이하여야 한다.")
    void maximumAmount() {
        BigDecimal amount = new BigDecimal("10000.001");

        assertThatThrownBy(() -> new CouponDiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 최대 할인 금액은 10000원 이하입니다.");
    }

    @Test
    @DisplayName("할인 금액은 500원 단위로만 설정할 수 있다.")
    void checkUnit() {
        BigDecimal amount = new BigDecimal(1300);

        assertThatThrownBy(() -> new CouponDiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로만 설정할 수 있습니다.");
    }
}
