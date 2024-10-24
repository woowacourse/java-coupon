package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumOrderAmountTest {

    @DisplayName("최소 주문 금액을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"5000", "100000"})
    void create(String minimumOrderAmount) {
        assertThatCode(() -> new MinimumOrderAmount(minimumOrderAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("최소 주문 금액이 5000원 미만, 100,000원 초과면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"4999", "100001"})
    void create_Fail2(String minimumOrderAmount) {
        assertThatThrownBy(() -> new MinimumOrderAmount(minimumOrderAmount))
                .isInstanceOf(CouponException.class);
    }

}
