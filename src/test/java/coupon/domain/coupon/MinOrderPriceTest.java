package coupon.domain.coupon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinOrderPriceTest {

    @ParameterizedTest
    @ValueSource(ints = {4999, 10001})
    void 최소_주문_금액은_5000원_이상_10000원_이하여야_한다(int minOrderPrice) {
        Assertions.assertThatThrownBy(() -> new MinOrderPrice(minOrderPrice));
    }
}
