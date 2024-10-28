package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DatePeriod {

    private final LocalDate startDateInclusive;
    private final LocalDate endDateExclusive;

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일과 종료일은 필수입니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
        this.startDateInclusive = startDate;
        this.endDateExclusive = endDate.plusDays(1);
    }

    public boolean isBetweenPeriod(LocalDateTime time) {
        LocalDateTime startPeriod = startDateInclusive.atStartOfDay();
        LocalDateTime endPeriod = endDateExclusive.atStartOfDay();
        if (startPeriod.isEqual(time)) {
            return true;
        }
        return time.isAfter(startPeriod) && time.isBefore(endPeriod);
    }

    public LocalDate getExpiredDate() {
        return endDateExclusive.minusDays(1);
    }
}
