package coupon.domain.coupon;

import java.time.LocalDate;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("특정 날짜에 발급 가능한 쿠폰인지 알 수 있다.")
    void canIssueAt() {
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 20));
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(coupon.canIssueAt(LocalDate.of(2024, 12, 19))).isFalse();
        softAssertions.assertThat(coupon.canIssueAt(LocalDate.of(2024, 12, 20))).isTrue();
        softAssertions.assertThat(coupon.canIssueAt(LocalDate.of(2024, 12, 21))).isFalse();

        softAssertions.assertAll();
    }
}
