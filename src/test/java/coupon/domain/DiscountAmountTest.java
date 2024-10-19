package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.exception.InvalidCouponDiscountAmount;

class DiscountAmountTest {

    @Test
    @DisplayName("할인 금액이 최소 금액 미만일 경우 예외가 발생한다.")
    void throw_exception_under_min_value() {
        assertThatCode(() -> new DiscountAmount(999))
                .isExactlyInstanceOf(InvalidCouponDiscountAmount.class);
    }

    @Test
    @DisplayName("할인 금액이 최대 금액 초과일 경우 예외가 발생한다.")
    void throw_exception_over_min_value() {
        assertThatCode(() -> new DiscountAmount(10001))
                .isExactlyInstanceOf(InvalidCouponDiscountAmount.class);
    }

    @Test
    @DisplayName("할인 금액이 금액 단위가 아닐 경우 예외가 발생한다.")
    void throw_exception_not_min_value() {
        assertThatCode(() -> new DiscountAmount(123))
                .isExactlyInstanceOf(InvalidCouponDiscountAmount.class);
    }
}
