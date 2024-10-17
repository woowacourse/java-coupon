package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import coupon.coupon.domain.CouponIssueDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponIssueDateTest {

    @Test
    @DisplayName("쿠폰 발급 기간이 정상 생성된다.")
    void createCouponIssueDate() {
        // given
        LocalDate issueStartDate = LocalDate.now().minusDays(1);
        LocalDate issueEndDate = LocalDate.now().plusDays(1);

        // when & then
        assertThatCode(() -> CouponIssueDate.createWithTime(issueStartDate, issueEndDate))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿠폰 발급 시작 날짜와 종료 날짜가 같은 경우 정상 생성된다.")
    void createCouponIssueDateWithSameDate() {
        // given
        LocalDate sameDate = LocalDate.of(2024, 10, 15);

        // when & then
        assertThatCode(() -> CouponIssueDate.createWithTime(sameDate, sameDate))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿠폰 발급 시작일은 종료일보다 이전이어야 한다.")
    void validateStartDateBeforeEndDate() {
        // given
        LocalDate issueStartDate = LocalDate.now().plusDays(1);
        LocalDate issueEndDate = LocalDate.now();

        // when & then
        assertThatCode(() -> CouponIssueDate.createWithTime(issueStartDate, issueEndDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 발급 시작일은 종료일 이전이어야 합니다.");
    }

    @ParameterizedTest
    @DisplayName("쿠폰을 발급받을 수 있는지 확인할 수 있다.")
    @CsvSource(value = {
            "2024-10-15T12:15:31, 2024-10-15, 2024-10-15, true",
            "2024-10-15T12:15:31, 2024-10-14, 2024-10-16, true",
            "2024-10-15T12:15:31, 2024-10-16, 2024-10-16, false",
            "2024-10-15T12:15:31, 2024-10-14, 2024-10-14, false",
    })
    void isIssuable(LocalDateTime now, LocalDate issueStartDate, LocalDate issueEndDate, boolean expected) {
        // given
        CouponIssueDate couponIssueDate = CouponIssueDate.createWithTime(issueStartDate, issueEndDate);

        // when
        boolean result = couponIssueDate.isIssuable(now);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
