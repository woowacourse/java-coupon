package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuePeriod {

    @Column(nullable = false)
    private LocalDateTime issueStartedAt;

    @Column(nullable = false)
    private LocalDateTime issueEndedAt;

    public IssuePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validateIssuePeriod(issueStartedAt, issueEndedAt);
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validateIssuePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt.isAfter(issueEndedAt)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 빠를 수 없습니다.");
        }
    }

    public boolean isIssuable(LocalDateTime now) {
        return now.isAfter(issueStartedAt) && now.isBefore(issueEndedAt);
    }
}
