package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.common.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponMinOrderAmountTest {

    @Test
    @DisplayName("쿠폰 최소 주문 금액이 정상 생성된다.")
    void createCouponMinOrderAmount() {
        // given
        Money minOrderAmount = Money.wons(6_000);

        // when & then
        assertThatCode(() -> new CouponMinOrderAmount(minOrderAmount))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("쿠폰 최소 주문 금액은 5,000원 이상 100,000원 이하이어야 한다.")
    @ValueSource(ints = {4_999, 100_001})
    void validateMinOrderAmountSize(int minOrderAmount) {
        // when & then
        assertThatCode(() -> new CouponMinOrderAmount(Money.wons(minOrderAmount)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 최소 금액은 5,000원 이상 100,000원 이하만 가능합니다.");
    }
}
