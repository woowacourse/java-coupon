package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final Category CATEGORY = Category.FASHION;

    @ParameterizedTest
    @CsvSource(value = {"1_000, 50_000", "1_500, 5_000"})
    void 할인율이_범위를_벗어나면_IllegalArgumentException이_발생한다(int discountAmount, int minOrderAmount) {
        // when & then
        assertThatThrownBy(() -> new Coupon("쿠폰", discountAmount, minOrderAmount, NOW, NOW, CATEGORY))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }
}
