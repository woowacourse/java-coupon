package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("쿠폰")
class CouponTest {

    @DisplayName("쿠폰의 이름이 30자 이상이면 예외가 발생한다.")
    @Test
    void validateName() {
        // given
        String name = "hellothisisveryverylongnamecoupon";

        // when & then
        assertThatThrownBy(() -> new Coupon(
                        name,
                        CouponCategory.FOOD,
                        1000,
                        30000,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 이름은 30자 이하여야 합니다.");
    }

    @DisplayName("쿠폰의 할인 금액이 1000원 미만, 10000원 초과면 예외가 발생한다.")
    @ValueSource(ints = {999, 10001})
    @ParameterizedTest
    void validateDiscountAmountRange(Integer discountAmount) {
        // when & then
        assertThatThrownBy(() -> new Coupon(
                        "name",
                        CouponCategory.FOOD,
                        discountAmount,
                        30000,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 할인 금액은 1000원 이상 10000원 이하여야 합니다.");
    }

    @DisplayName("쿠폰의 할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    @Test
    void validateDiscountAmount() {
        // given
        Integer discountAmount = 1300;

        // when & then
        assertThatThrownBy(() -> new Coupon(
                        "name",
                        CouponCategory.FOOD,
                        discountAmount,
                        30000,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 할인 금액은 500원 단위로 설정해야 합니다.");
    }

    @DisplayName("쿠폰의 최소 주문 금액이 5000원 미만, 10000 초과면 예외가 발생한다.")
    @ValueSource(ints = {4999, 100001})
    @ParameterizedTest
    void validateMinOrderAmount(Integer minOrderAmount) {
        // when & then
        assertThatThrownBy(() -> new Coupon(
                        "name",
                        CouponCategory.FOOD,
                        1000,
                        minOrderAmount,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
    }

    @DisplayName("쿠폰의 할인율이 3% 미만이면 예외가 발생한다.")
    @Test
    void validateMinDiscountRate() {
        // given
        Integer discountAmount = 3000;

        // when & then
        assertThatThrownBy(() -> new Coupon(
                        "name",
                        CouponCategory.FOOD,
                        discountAmount,
                        10000,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @DisplayName("쿠폰의 할인율이 20% 미만이면 예외가 발생한다.")
    @Test
    void validateMaxDiscountRate() {
        // given
        Integer discountAmount = 6000;

        // when & then
        assertThatThrownBy(() -> new Coupon(
                        "name",
                        CouponCategory.FOOD,
                        discountAmount,
                        10000,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @DisplayName("쿠폰의 종료일자가 시작일자보다 빠르면 예외가 발생한다.")
    @Test
    void validateIssuedPeriod() {
        // given
        LocalDateTime startedAt = LocalDateTime.now();
        LocalDateTime endedAt = LocalDateTime.now().minusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(
                        "name",
                        CouponCategory.FOOD,
                        1000,
                        30000,
                        startedAt,
                        endedAt
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 종료일은 시작일보다 이후여야 합니다.");
    }
}
