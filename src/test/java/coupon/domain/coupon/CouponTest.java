package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
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

    @Test
    void 쿠폰이_발급_가능한_기간이_아닐_때_발급할_경우_예외가_발생한다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-14");
        LocalDate issueEndedDate = LocalDate.parse("2024-10-15");
        Coupon coupon = new Coupon("쿠폰", 1_000, 5_000, issueStartedDate, issueEndedDate, CATEGORY);
        LocalDateTime now = LocalDateTime.parse("2024-10-13T23:59:59.999999");

        // when & then
        assertThatThrownBy(() -> coupon.issue(now))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 발급 기간이 아닙니다.");
    }
}
