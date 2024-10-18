package coupon;

import coupon.config.MinimumOrderPriceConfig;
import coupon.domain.MinimumOrderPriceValidator;
import coupon.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MinimumOrderPriceValidatorTest {
    MinimumOrderPriceValidator minimumOrderPriceValidator = new MinimumOrderPriceValidator(
            new MinimumOrderPriceConfig(
                    new BigDecimal(5_000),
                    new BigDecimal(100_000)
            ));

    @Test
    @DisplayName("최소 / 최대 주문 금액 이내면, 예외를 발생하지 않는다.")
    void does_not_throw_exception_when_range_in_min_max() {
        assertThatCode(() -> minimumOrderPriceValidator.validate(Money.from(5_000)))
                .doesNotThrowAnyException();

        assertThatCode(() -> minimumOrderPriceValidator.validate(Money.from(100_000)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("최소 주문 금액 미만이면, 예외를 발생한다.")
    void throw_exception_when_under_min_price() {
        assertThatThrownBy(() -> minimumOrderPriceValidator.validate(Money.from(4_999)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("최대 주문 금액 초과면, 예외를 발생한다.")
    void throw_exception_when_under_max_price() {
        assertThatThrownBy(() -> minimumOrderPriceValidator.validate(Money.from(100_001)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
