package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class IssuePeriod {

    private static final LocalTime START_AT_TIME = LocalTime.of(0, 0, 0, 0);
    private static final LocalTime END_AT_TIME = LocalTime.of(23, 59, 59, 999999);

    @Column(name = "issue_start_at", nullable = false)
    private LocalDateTime issueStartAt;

    @Column(name = "issue_end_at", nullable = false)
    private LocalDateTime issueEndAt;

    public IssuePeriod(LocalDate issueStartAt, LocalDate issueEndAt) {
        validate(issueStartAt, issueEndAt);
        this.issueStartAt = issueStartAt.atTime(START_AT_TIME);
        this.issueEndAt = issueEndAt.atTime(END_AT_TIME);
    }

    private void validate(LocalDate issueStartAt, LocalDate issueEndAt) {
        requireNonnull(issueStartAt, issueEndAt);
        requireStartBeforeEnd(issueStartAt, issueEndAt);
    }

    private void requireNonnull(LocalDate issueStartAt, LocalDate issueEndAt) {
        if (issueStartAt == null || issueEndAt == null) {
            throw new NullPointerException("발급 시작일과 종료일을 입력해야 합니다.");
        }
    }

    private void requireStartBeforeEnd(LocalDate issueStartAt, LocalDate issueEndAt) {
        if (issueStartAt.isAfter(issueEndAt)) {
            throw new IllegalArgumentException("발급 시작일은 종료일 이후일 수 없습니다.");
        }
    }
}
