package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @DisplayName("할인 금액을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1000", "10000"})
    void create() {
        assertThatCode(() -> new DiscountAmount("10000"))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    @Test
    void create_Fail1() {
        assertThatThrownBy(() -> new DiscountAmount("1012"))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("할인 금액이 천원 미만, 만원 초과면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"999", "10001"})
    void create_Fail2(String discountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(CouponException.class);
    }
}
