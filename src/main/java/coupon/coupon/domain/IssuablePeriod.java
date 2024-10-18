package coupon.coupon.domain;

import jakarta.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class IssuablePeriod {

    private static final LocalTime START_TIME = LocalTime.of(0, 0, 0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 59, 59, 999999000);

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    public IssuablePeriod(){
    }

    public IssuablePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        validatePeriodTime(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public IssuablePeriod(LocalDate startDate, LocalDate endDate) {
        this(LocalDateTime.of(startDate, START_TIME), LocalDateTime.of(endDate, END_TIME));
    }

    private void validatePeriodTime(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작일은 종료일과 같거나 이전이어야 합니다.");
        }
    }

    public boolean canIssue(LocalDateTime issueRequestTime) {
        return !issueRequestTime.isBefore(startAt) && !issueRequestTime.isAfter(endAt);
    }
}
