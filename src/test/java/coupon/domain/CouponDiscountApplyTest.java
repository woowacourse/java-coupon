package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponDiscountApplyTest {

    @ParameterizedTest
    @CsvSource(delimiterString = ":", value = {"1000:30000", "2000:10000"})
    @DisplayName("쿠폰 할인 적용 객체를 생성한다.")
    void create(Long discountSource, Long limitSource) {
        BigDecimal discountAmount = new BigDecimal(discountSource);
        BigDecimal applicableAmount = new BigDecimal(limitSource);

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
