package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuePeriod {

    @Column(nullable = false)
    private LocalDateTime issueStartedAt;

    @Column(nullable = false)
    private LocalDateTime issueEndedAt;

    protected IssuePeriod(LocalDate issueStartedDate, LocalDate issueEndedDate) {
        validateIssuePeriod(issueStartedDate, issueEndedDate);
        this.issueStartedAt = issueStartedDate.atTime(0, 0, 0, 0);
        this.issueEndedAt = issueEndedDate.atTime(23, 59, 59, 999_999_000);
    }

    private void validateIssuePeriod(LocalDate issueStartedDate, LocalDate issueEndedDate) {
        if (issueStartedDate == null || issueEndedDate == null) {
            throw new IllegalArgumentException("발급 시작일과 종료일은 필수입니다.");
        }

        if (issueStartedDate.isAfter(issueEndedDate)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 빠를 수 없습니다.");
        }
    }

    public boolean isIssuable(LocalDateTime now) {
        return now.isAfter(issueStartedAt) && now.isBefore(issueEndedAt);
    }
}
