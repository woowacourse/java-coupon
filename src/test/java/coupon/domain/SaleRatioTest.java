package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.SaleRatio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SaleRatioTest {

    @DisplayName("최대 할인 비율을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxRatio() {
        assertThatThrownBy(() -> new SaleRatio(0.21))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 할인 비율 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinRatio() {
        assertThatThrownBy(() -> new SaleRatio(0.09))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 할인 비율을 생성할 수 있다")
    @ValueSource(doubles = {20.0, 3.0, 19.9, 3.1})
    @ParameterizedTest
    void createSalePrice(double validSaleRatio) {
        assertThatCode(() -> new SaleRatio(validSaleRatio))
                .doesNotThrowAnyException();
    }
}
