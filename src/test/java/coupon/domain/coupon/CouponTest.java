package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.support.CouponFixtureGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @DisplayName("할인율은 3% 이상, 20% 이하여야 한다.")
    @Nested
    class validateRate {

        @ParameterizedTest
        @ValueSource(ints = {33_333, 5_000})
        void validateRateSuccess(int minOrderAmount) {
            assertThatCode(() -> CouponFixtureGenerator.generate(1_000, minOrderAmount))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {75_000, 5_000})
        void validateRateException(int minOrderAmount) {
            assertThatThrownBy(() -> CouponFixtureGenerator.generate(1_500, minOrderAmount))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
