package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class IssuedDuration {

    private LocalDateTime issueStartedAt;
    private LocalDateTime issueEndedAt;

    public IssuedDuration(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validate(issueStartedAt, issueEndedAt);
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validate(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validateIsNull(issueStartedAt, issueEndedAt);
        validateOrder(issueStartedAt, issueEndedAt);
    }

    private void validateIsNull(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt == null) {
            throw new IllegalArgumentException("발급 시작일은 null일 수 없습니다.");
        }
        if (issueEndedAt == null) {
            throw new IllegalArgumentException("발급 종료일은 null일 수 없습니다.");
        }
    }

    private void validateOrder(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (startedAt.isAfter(endedAt)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다: %s %s".formatted(startedAt, endedAt));
        }
    }
}
