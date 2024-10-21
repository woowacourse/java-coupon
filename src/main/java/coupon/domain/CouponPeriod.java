package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponPeriod {

    @Column(name = "issue_started_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime issueEndedAt;

    public CouponPeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
        validatePeriod(issueStartedAt, issueEndedAt);
    }

    private void validatePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt.isAfter(issueEndedAt)) {
            throw new IllegalArgumentException("쿠폰의 발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    public boolean isIssuable() {
        return issueStartedAt.isAfter(LocalDateTime.now()) || issueEndedAt.isBefore(LocalDateTime.now());
    }
}
