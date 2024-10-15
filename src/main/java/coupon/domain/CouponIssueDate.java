package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponIssueDate {

    @Column(name = "issue_start_date", nullable = false)
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_end_date", nullable = false)
    private LocalDateTime issueEndedAt;

    private CouponIssueDate(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    public static CouponIssueDate createWithTime(LocalDate issueStartDate, LocalDate issueEndDate) {
        validateStartDateBeforeEndDate(issueStartDate, issueEndDate);

        LocalDateTime issueStartedAt = LocalDateTime.of(issueStartDate, LocalTime.MIN);
        LocalDateTime issueEndedAt = LocalDateTime.of(issueEndDate, LocalTime.MAX).withNano(999999000);

        return new CouponIssueDate(issueStartedAt, issueEndedAt);
    }

    private static void validateStartDateBeforeEndDate(LocalDate issueStartDate, LocalDate issueEndDate) {
        if (issueStartDate.isAfter(issueEndDate)) {
            String message = "쿠폰 발급 시작일은 종료일 이전이어야 합니다.";

            throw new IllegalArgumentException(message);
        }
    }

    public boolean isIssuable(LocalDateTime dateTime) {
        return (issueStartedAt.isBefore(dateTime) || issueStartedAt.isEqual(dateTime))
               && (issueEndedAt.isAfter(dateTime) || issueEndedAt.isEqual(dateTime));
    }
}
