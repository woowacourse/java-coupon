package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.SaleOrderPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SaleOrderPriceTest {

    @DisplayName("최대 최소 주문금액 가격을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxOrderPrice() {
        int exceededPrice = 10001;
        assertThatThrownBy(() -> new SaleOrderPrice(exceededPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 할인 가격 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinOrderPrice() {
        int shortagePrice = 4999;
        assertThatThrownBy(() -> new SaleOrderPrice(shortagePrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 할인 가격은 단위 가격으로 나뉘어떨어져야한다")
    @ValueSource(ints = {5000, 10000})
    @ParameterizedTest
    void createSalePrice(int validSalePrice) {
        assertThatCode(() -> new SaleOrderPrice(validSalePrice))
                .doesNotThrowAnyException();
    }
}
