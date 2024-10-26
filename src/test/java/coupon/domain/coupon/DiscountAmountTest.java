package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @Test
    void 천원_미만이면_예외가_발생한다() {
        assertThatThrownBy(() -> new DiscountAmount(new BigDecimal(999)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 만원을_초과하면_예외가_발생한다() {
        assertThatThrownBy(() -> new DiscountAmount(new BigDecimal(10001)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 오백원_단위로_나누어지지않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new DiscountAmount(new BigDecimal(600)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
