package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponApplicableAmountTest {

    @Test
    @DisplayName("최소 적용 가능 금액을 생성한다.")
    void create() {
        BigDecimal amount = new BigDecimal(5000);

        assertThatCode(() -> new CouponApplicableAmount(amount))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 적용 가능 금액은 5,000원 이상이어야 한다.")
    void minimumAmount() {
        BigDecimal amount = new BigDecimal("4999.999");

        assertThatThrownBy(() -> new CouponApplicableAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 쿠폰의 최소 적용 가능 금액은 5000원 이상입니다.");
    }

    @Test
    @DisplayName("최소 적용 가능 금액은 100,000원 이하여야 한다.")
    void maximumAmount() {
        BigDecimal amount = new BigDecimal("100000.001");

        assertThatThrownBy(() -> new CouponApplicableAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 쿠폰의 최대 적용 가능 금액은 100000원 이하입니다.");
    }
}
