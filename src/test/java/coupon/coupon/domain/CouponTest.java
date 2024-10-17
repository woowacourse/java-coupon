package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CouponTest {
    private static final Long MIN_DISCOUNT_RATE = 3L;
    private static final Long MAX_DISCOUNT_RATE = 20L;

    @DisplayName("할인율이 최소 할인율보다 적으면, 예외를 발생한다.")
    @Test
    void testValidateDiscountRate_WhenDiscountRateBiggerThanMaximum() {
        // given
        Long discountAmount = 200L;
        Long minimumOrderAmount = 10000L;

        // when & then
        assertThatThrownBy(() -> new Coupon("name", discountAmount, minimumOrderAmount, "category", LocalDateTime.now(), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율이 " + MIN_DISCOUNT_RATE + "% 이상, " + MAX_DISCOUNT_RATE + "% 이하여야 합니다.");
    }

    @DisplayName("할인율이 최대 할인율보다 크면, 예외를 발생한다.")
    @Test
    void testValidateDiscountRate_WhenDiscountRateSmallerThanMinimum() {
        // given
        Long discountAmount = 2100L;
        Long minimumOrderAmount = 10000L;

        // when & then
        assertThatThrownBy(() -> new Coupon("name", discountAmount, minimumOrderAmount, "category", LocalDateTime.now(), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율이 " + MIN_DISCOUNT_RATE + "% 이상, " + MAX_DISCOUNT_RATE + "% 이하여야 합니다.");
    }
}
