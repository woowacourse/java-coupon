package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponDiscountAmountTest {

    @Test
    @DisplayName("쿠폰 할인 금액이 정상 생성된다.")
    void createCouponDiscountAmount() {
        // given
        int discountAmount = 5_000;
        CouponMinOrderAmount minOrderAmount = new CouponMinOrderAmount(30_000);

        // when & then
        assertThatCode(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("쿠폰 할인 금액은 1,000원 이상 10,000원 이하만 가능하다.")
    @ValueSource(ints = {999, 10_001})
    void validateDiscountAmountSize(int discountAmount) {
        // given
        CouponMinOrderAmount minOrderAmount = new CouponMinOrderAmount(30_000);

        // when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하만 가능합니다.");
    }

    @ParameterizedTest
    @DisplayName("쿠폰 할인 금액은 500원 단위여야 한다.")
    @ValueSource(ints = {1_001, 1_499})
    void validateDiscountAmountUnit(int discountAmount) {
        // given
        CouponMinOrderAmount minOrderAmount = new CouponMinOrderAmount(30_000);

        // when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로만 가능합니다.");
    }

    @ParameterizedTest
    @DisplayName("쿠폰 할인율은 3% 이상 20% 이하만 가능하다.")
    @ValueSource(ints = {1_000, 10_000})
    void validateDiscountRate(int discountAmount) {
        // given
        CouponMinOrderAmount minOrderAmount = new CouponMinOrderAmount(35_000);

        // when & then
        assertThatThrownBy(() -> new CouponDiscountAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하만 가능합니다.");
    }
}
