package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class DatePeriod {

    private static final LocalTime START_TIME = LocalTime.of(0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 59, 59, 999_999_000);

    private final LocalDate startDate;
    private final LocalDate endDate;

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수입니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBetweenPeriod(LocalDateTime time) {
        LocalDateTime startPeriod = startDate.atTime(START_TIME);
        LocalDateTime endPeriod = endDate.atTime(END_TIME);
        if (startPeriod.isEqual(time) || endPeriod.isEqual(time)) {
            return true;
        }
        return time.isAfter(startPeriod) && time.isBefore(endPeriod);
    }

    public LocalDateTime getEndDateTime() {
        return endDate.atTime(END_TIME);
    }
}
