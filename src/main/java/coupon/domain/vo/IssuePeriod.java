package coupon.domain.vo;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class IssuePeriod {

    @Column(name = "issue_started_at", nullable = false, columnDefinition = "datetime(6)")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", nullable = false, columnDefinition = "datetime(6)")
    private LocalDateTime issueEndedAt;

    public IssuePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validate(issueStartedAt, issueEndedAt);
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validate(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt.isAfter(issueEndedAt)) {
            throw new GlobalCustomException(ErrorMessage.INVALID_ISSUE_PERIOD);
        }
    }
}
