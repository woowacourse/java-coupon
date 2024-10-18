package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SalePriceTest {

    @DisplayName("최대 할인 가격을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxPrice() {
        int exceededPrice = 10001;
        assertThatThrownBy(() -> new SalePrice(exceededPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 할인 가격 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinPrice() {
        int shortagePrice = 499;
        assertThatThrownBy(() -> new SalePrice(shortagePrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("실패 : 할인 가격은 단위 가격으로 나뉘어떨어져야 한다")
    @ValueSource(ints = {501, 999, 1501})
    @ParameterizedTest
    void throwIllegalArgumentException_When_NullOrEmpty(int invalidSalePrice) {
        assertThatThrownBy(() -> new SalePrice(invalidSalePrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 할인 가격은 단위 가격으로 나뉘어떨어져야한다")
    @ValueSource(ints = {1000, 1500, 2000})
    @ParameterizedTest
    void createSalePrice(int validSalePrice) {
        assertThatCode(() -> new SalePrice(validSalePrice))
                .doesNotThrowAnyException();
    }
}
