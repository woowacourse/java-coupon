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
        validateOrder(issueStartedAt, issueEndedAt);
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validateOrder(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (startedAt.isAfter(endedAt)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다.");
        }
    }
}
