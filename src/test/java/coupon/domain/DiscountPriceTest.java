package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.DiscountPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountPriceTest {

    private static final int MIN_DISCOUNT_PRICE = 1000;
    private static final int MAX_DISCOUNT_PRICE = 10000;
    private static final int UNIT_DISCOUNT_PRICE = 500;

    @DisplayName("최대 할인 가격을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxPrice() {
        int exceededPrice = MAX_DISCOUNT_PRICE + 1;
        assertThatThrownBy(() -> new DiscountPrice(exceededPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 할인 가격 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinPrice() {
        int shortagePrice = MIN_DISCOUNT_PRICE - 1;
        assertThatThrownBy(() -> new DiscountPrice(shortagePrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("실패 : 할인 가격은 단위 가격으로 나뉘어떨어져야 한다")
    @ValueSource(ints = {UNIT_DISCOUNT_PRICE + 1})
    @ParameterizedTest
    void throwIllegalArgumentException_When_NullOrEmpty(int invalidSalePrice) {
        assertThatThrownBy(() -> new DiscountPrice(invalidSalePrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 할인 가격을 생성할 수 있다")
    @ValueSource(ints = {1000, 1500, 2000})
    @ParameterizedTest
    void createSalePrice(int validSalePrice) {
        assertThatCode(() -> new DiscountPrice(validSalePrice))
                .doesNotThrowAnyException();
    }
}
