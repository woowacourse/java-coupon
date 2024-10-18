package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class IssuePeriodTest {

    @Test
    void 발급_기간_객체를_생성한다() {
        // given
        LocalDateTime issueStartedAt = LocalDateTime.parse("2024-10-14T00:00:00");
        LocalDateTime issueEndedAt = LocalDateTime.parse("2024-10-20T23:59:59");

        // when
        IssuePeriod issuePeriod = new IssuePeriod(issueStartedAt, issueEndedAt);

        // then
        assertSoftly(softly -> {
            softly.assertThat(issuePeriod.getIssueStartedAt()).isEqualTo(issueStartedAt);
            softly.assertThat(issuePeriod.getIssueEndedAt()).isEqualTo(issueEndedAt);
        });
    }

    @Test
    void 발급_시작일이_종료일보다_빠르면_IllegalArgumentException이_발생한다() {
        // given
        LocalDateTime issueStartedAt = LocalDateTime.parse("2024-10-18T00:00:01");
        LocalDateTime issueEndedAt = LocalDateTime.parse("2024-10-18T00:00:00");

        // when & then
        assertThatThrownBy(() -> new IssuePeriod(issueStartedAt, issueEndedAt))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일은 종료일보다 빠를 수 없습니다.");
    }
}
