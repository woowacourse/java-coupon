package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountPriceTest {

    @Test
    @DisplayName("실패 : 1000 이하")
    void failUnderMinName() {
        assertThatThrownBy(() -> new DiscountPrice(500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + 1_000 + "원 이상이어야 한다.");
    }

    @Test
    @DisplayName("실패 : 10000 이상")
    void failOverMaxName() {
        assertThatThrownBy(() -> new DiscountPrice(10_500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + 10_000 + "원 이하여야 한다.");
    }

    @Test
    @DisplayName("실패 : 500 단위")
    void failUnitName() {
        assertThatThrownBy(() -> new DiscountPrice(1499))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 " + 500 + "원 단위로 설정할 수 있다.");
    }
}
