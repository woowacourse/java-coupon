package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountPercentTest {

    @DisplayName("할인율이 최소 할인율보다 적거나 최대 할인율보다 많으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 21, 22, 23, 100})
    void throwsExceptionWhenNotInRange(int percent) {
        String expectedMessage = "할인율은 %d 이상 %d 이하여야 합니다. 입력 값: ".formatted(3, 20) + percent;

        assertThatThrownBy(() -> new DiscountPercent(percent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }
}
