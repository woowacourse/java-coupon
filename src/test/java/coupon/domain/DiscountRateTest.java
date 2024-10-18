package coupon.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiscountRateTest {

    @Test
    void 할인율은_할인금액_나누기_최소_주문금액으로_계산된다() {
        DiscountRate discountRate = new DiscountRate(1000, 5000);
        assertThat(discountRate.intValue()).isEqualTo(20);  // 1000 / 5000 = 0.2 -> 20%
    }

    @Test
    void 할인율은_소수점_버림율_처리된다() {
        DiscountRate discountRate = new DiscountRate(1000, 30000);
        assertThat(discountRate.intValue()).isEqualTo(3);  // 1500 / 5100 = 0.294 -> 버림 처리하여 29%
    }

    @Test
    void 할인율은_3퍼센트_이상이어야_한다() {
        assertThatThrownBy(() -> new DiscountRate(1000, 34000))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인율은 3% 이상이어야 합니다.");
    }

    @Test
    void 할인율은_20퍼센트_이하여야_한다() {
        assertThatThrownBy(() -> new DiscountRate(3000, 5000))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인율은 20% 이하여야 합니다.");
    }
}
