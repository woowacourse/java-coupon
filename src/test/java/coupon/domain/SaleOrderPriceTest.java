package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.SaleOrderPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SaleOrderPriceTest {

    private static final int MIN_SALE_PRICE = 5000;
    private static final int MAX_SALE_PRICE = 10000;

    @DisplayName("최대 최소 주문금액 가격을 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_ExceededThanMaxOrderPrice() {
        int exceededPrice = MAX_SALE_PRICE + 1;
        assertThatThrownBy(() -> new SaleOrderPrice(exceededPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 주문 금액 가격 미만일 수 없다")
    @Test
    void throwIllegalArgumentException_When_ShorterThanMinOrderPrice() {
        int shortagePrice = MIN_SALE_PRICE - 1;
        assertThatThrownBy(() -> new SaleOrderPrice(shortagePrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("성공: 최소 주문금액 가격을 생성할 수 있다")
    @ValueSource(ints = {5000, 10000})
    @ParameterizedTest
    void createSalePrice(int validSalePrice) {
        assertThatCode(() -> new SaleOrderPrice(validSalePrice))
                .doesNotThrowAnyException();
    }
}
