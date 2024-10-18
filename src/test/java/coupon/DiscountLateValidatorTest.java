package coupon;

import coupon.config.DiscountLateValidationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountLateValidatorTest {
    DiscountLateValidator discountLateValidator = new DiscountLateValidator(
            new DiscountLateValidationConfig(3, 20));

    @Test
    @DisplayName("최소 / 최대 할인율 이내면, 예외를 발생하지 않는다.")
    void does_not_throw_exception_when_range_in_min_max() {
        assertThatCode(() -> discountLateValidator.validate(new BigDecimal(15)))
                .doesNotThrowAnyException();

        assertThatCode(() -> discountLateValidator.validate(new BigDecimal(3)))
                .doesNotThrowAnyException();

        assertThatCode(() -> discountLateValidator.validate(new BigDecimal(20)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 할인율 미만이면, 예외를 발생한다.")
    void throw_exception_when_under_min_rate() {
        assertThatThrownBy(() -> discountLateValidator.validate(new BigDecimal(2.999999999)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최대 할인율 초과면, 예외를 발생한다.")
    void throw_exception_when_under_max_rate() {
        assertThatThrownBy(() -> discountLateValidator.validate(new BigDecimal(20.000001)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
