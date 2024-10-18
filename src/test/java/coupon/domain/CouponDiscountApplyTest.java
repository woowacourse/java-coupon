package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponDiscountApplyTest {

    @Test
    @DisplayName("쿠폰 할인 적용 객체를 생성한다.")
    void create() {
        BigDecimal discountAmount = new BigDecimal(1000L);
        BigDecimal applicableAmount = new BigDecimal(10000L);

        assertThatCode(() -> new CouponDiscountApply(discountAmount, applicableAmount))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("할인율은 3% 이상이어야 한다.")
    void minimumDiscountPercent() {
        BigDecimal discountAmount = new BigDecimal(1000L);
        BigDecimal applicableAmount = new BigDecimal(40000L);

        assertThatThrownBy(() -> new CouponDiscountApply(discountAmount, applicableAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상이어야 합니다.");
    }

    @Test
    @DisplayName("할인율은 20% 이하이어야 한다.")
    void maximumDiscountPercent() {
        BigDecimal discountAmount = new BigDecimal(8000L);
        BigDecimal applicableAmount = new BigDecimal(38000L);

        assertThatThrownBy(() -> new CouponDiscountApply(discountAmount, applicableAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 20% 이하여야 합니다.");
    }
}
