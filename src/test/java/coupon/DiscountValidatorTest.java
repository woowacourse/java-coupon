package coupon;

import coupon.config.DiscountValidationConfig;
import coupon.domain.DiscountValidator;
import coupon.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountValidatorTest {

    DiscountValidator discountValidator = new DiscountValidator(
            new DiscountValidationConfig(
                    new BigDecimal(1000),
                    new BigDecimal(10_000),
                    new BigDecimal(500)));

    @Test
    @DisplayName("최소 / 최대 할인율 이내면, 예외를 발생하지 않는다.")
    void does_not_throw_exception_when_range_in_min_max() {
        assertThatCode(() -> discountValidator.validate(Money.from(1000)))
                .doesNotThrowAnyException();

        assertThatCode(() -> discountValidator.validate(Money.from(10_000)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 할인율 미만이면, 예외를 발생한다.")
    void throw_exception_when_under_min_rate() {
        assertThatThrownBy(() -> discountValidator.validate(Money.from(500)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최대 할인율 초과면, 예외를 발생한다.")
    void throw_exception_when_under_max_rate() {
        assertThatThrownBy(() -> discountValidator.validate(Money.from(10_500)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("단위로 나눠지지 않으면, 예외를 발생한다.")
    void throw_exception_when_not_divide_unit() {
        assertThatThrownBy(() -> discountValidator.validate(Money.from(9_499)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
