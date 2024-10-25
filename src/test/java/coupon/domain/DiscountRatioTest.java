package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.DiscountRatio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountRatioTest {

    private static final int MIN_DISCOUNT_RATIO = 3;
    private static final int MAX_DISCOUNT_RATIO = 20;

    @DisplayName("최대 할인율을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxRatio() {
        assertThatThrownBy(() -> new DiscountRatio(MAX_DISCOUNT_RATIO + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 할인율 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinRatio() {
        assertThatThrownBy(() -> new DiscountRatio(MIN_DISCOUNT_RATIO - 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 할인율을 생성할 수 있다")
    @ValueSource(ints = {MAX_DISCOUNT_RATIO, MIN_DISCOUNT_RATIO, MAX_DISCOUNT_RATIO - 1, MIN_DISCOUNT_RATIO + 1})
    @ParameterizedTest
    void createSalePrice(int validSaleRatio) {
        assertThatCode(() -> new DiscountRatio(validSaleRatio))
                .doesNotThrowAnyException();
    }
}
