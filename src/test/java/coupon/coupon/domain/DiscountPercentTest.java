package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountPercentTest {

    @Test
    @DisplayName("실패 : 2% 이하")
    void failUnderMinName() {
        assertThatThrownBy(() -> new DiscountPercent(2, 100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 " + 3.0 + "% 이상이어야 한다.");
    }

    @Test
    @DisplayName("실패 : 21% 이상")
    void failOverMaxName() {
        assertThatThrownBy(() -> new DiscountPercent(22, 100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 " + 20.0 + "% 이하여야 한다.");
    }
}
