package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
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
    void 발급_시작일과_종료일이_같으면_당일에만_발급할_수_있는_쿠폰이_된다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-14");
        LocalDate issueEndedDate = LocalDate.parse("2024-10-14");

        // when
        IssuePeriod issuePeriod = new IssuePeriod(issueStartedDate, issueEndedDate);

        // then
        assertSoftly(softly -> {
            softly.assertThat(issuePeriod.getIssueStartedAt())
                    .isEqualTo(LocalDateTime.parse("2024-10-14T00:00:00"));
            softly.assertThat(issuePeriod.getIssueEndedAt())
                    .isEqualTo(LocalDateTime.parse("2024-10-14T23:59:59.999999"));
        });
    }

    @Test
    void 발급_시작일이_null이면_IllegalArgumentException이_발생한다() {
        // given
        LocalDate issueStartedDate = null;
        LocalDate issueEndedDate = LocalDate.parse("2024-10-14");

        // when & then
        assertThatThrownBy(() -> new IssuePeriod(issueStartedDate, issueEndedDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일과 종료일은 필수입니다.");
    }

    @Test
    void 발급_종료일이_null이면_IllegalArgumentException이_발생한다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-14");
        LocalDate issueEndedDate = null;

        // when & then
        assertThatThrownBy(() -> new IssuePeriod(issueStartedDate, issueEndedDate))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일과 종료일은 필수입니다.");
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

    @Test
    void 발급_가능한_기간이_아니면_false를_반환한다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-14");
        LocalDate issueEndedDate = LocalDate.parse("2024-10-15");
        IssuePeriod issuePeriod = new IssuePeriod(issueStartedDate, issueEndedDate);
        LocalDateTime now = LocalDateTime.parse("2024-10-16T00:00:00");

        // when
        boolean issuable = issuePeriod.isIssuable(now);

        // then
        assertThat(issuable).isFalse();
    }

    @Test
    void 발급_가능한_기간이면_true를_반환한다() {
        // given
        LocalDate issueStartedDate = LocalDate.parse("2024-10-14");
        LocalDate issueEndedDate = LocalDate.parse("2024-10-15");
        IssuePeriod issuePeriod = new IssuePeriod(issueStartedDate, issueEndedDate);
        LocalDateTime now = LocalDateTime.parse("2024-10-14T00:00:00.000001");

        // when
        boolean issuable = issuePeriod.isIssuable(now);

        // then
        assertThat(issuable).isTrue();
    }
}
