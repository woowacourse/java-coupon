package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class IssuePeriodTest {

    @Test
    void 발급_기간_객체를_생성한다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-14");
        LocalDate issueEndedDate = LocalDate.parse("2024-10-15");

        // when
        IssuePeriod issuePeriod = new IssuePeriod(issueStartedDate, issueEndedDate);

        // then
        assertSoftly(softly -> {
            softly.assertThat(issuePeriod.getIssueStartedAt())
                    .isEqualTo(LocalDateTime.parse("2024-10-14T00:00:00"));
            softly.assertThat(issuePeriod.getIssueEndedAt())
                    .isEqualTo(LocalDateTime.parse("2024-10-15T23:59:59.999999"));
        });
    }

    @Test
    void 발급_시작일이_종료일보다_빠르면_IllegalArgumentException이_발생한다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-19");
        LocalDate issueEndedDate = LocalDate.parse("2024-10-18");

        // when & then
        assertThatThrownBy(() -> new IssuePeriod(issueStartedDate, issueEndedDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일은 종료일보다 빠를 수 없습니다.");
    }
}
