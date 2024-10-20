package coupon.coupon.domain;

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

    @Column(nullable = false, name = "issue_start_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime startAt;

    @Column(nullable = false, name = "issue_end_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime endAt;

    public IssuePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        validateIssuePeriod(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateIssuePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("쿠폰 발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
