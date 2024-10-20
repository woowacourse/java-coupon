package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.DiscountRatio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountRatioTest {

    @DisplayName("최대 할인 비율을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxRatio() {
        assertThatThrownBy(() -> new DiscountRatio(21))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 할인 비율 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinRatio() {
        assertThatThrownBy(() -> new DiscountRatio(2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 할인 비율을 생성할 수 있다")
    @ValueSource(ints = {20, 3, 19, 4})
    @ParameterizedTest
    void createSalePrice(int validSaleRatio) {
        assertThatCode(() -> new DiscountRatio(validSaleRatio))
                .doesNotThrowAnyException();
    }
}
