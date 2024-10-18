package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CouponAmountTest {

    private final int validDiscountAmount = 5_000;
    private final int validMinOrderAmount = 5_000;

    @Test
    void 예외_할인금액이_1000_미만일때() {
        // given
        int discountAmount = 500;
        int minOrderAmount = validMinOrderAmount;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상이어야 합니다: " + discountAmount);
    }

    @Test
    void 예외_할인금액이_10000_초과일때() {
        // given
        int discountAmount = 50_000;
        int minOrderAmount = validMinOrderAmount;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 10,000원 이하이어야 합니다: " + discountAmount);
    }

    @Test
    void 예외_최소주문금액이_5000_미만일때() {
        // given
        int discountAmount = validDiscountAmount;
        int minOrderAmount = 500;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상이어야 합니다: " + minOrderAmount);
    }

    @Test
    void 예외_최소주문금액이_100000_초과일때() {
        // given
        int discountAmount = validDiscountAmount;
        int minOrderAmount = 5000_000;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 100,000원 이하이어야 합니다: " + minOrderAmount);
    }

    @Test
    void 예외_할인금액이_500원단위가_아닐때() {
        // given
        int discountAmount = 1234;
        int minOrderAmount = validMinOrderAmount;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정해야 합니다: " + discountAmount);
    }

    @Test
    void 예외_할인율이_3퍼미만일때() {
        // given
        int discountAmount = 1_000;
        int minOrderAmount = 100_000;

        // when & then
        int discountRate = discountAmount * 100 / minOrderAmount;
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상이어야 합니다: " + discountRate);
    }

    @Test
    void 예외_할인율이_20퍼초과일때() {
        // given
        int discountAmount = 5_000;
        int minOrderAmount = 5_000;

        // when & then
        int discountRate = discountAmount * 100 / minOrderAmount;
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 20% 이하이어야 합니다: " + discountRate);
    }

    @Test
    void 예외_할인금액이_null일때() {
        // given
        Integer discountAmount = null;
        Integer minOrderAmount = 5_000;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 null일 수 없습니다.");
    }

    @Test
    void 예외_최소주문금액이_null일때() {
        // given
        Integer discountAmount = 5_000;
        Integer minOrderAmount = null;

        // when & then
        assertThatThrownBy(() -> new CouponAmount(discountAmount, minOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 null일 수 없습니다.");
    }
}
