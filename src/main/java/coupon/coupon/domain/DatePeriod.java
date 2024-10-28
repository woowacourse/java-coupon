package coupon.coupon.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class DatePeriod {

    private static final LocalTime startTime = LocalTime.of(0, 0, 0);
    private static final LocalTime endTime = LocalTime.of(23, 59, 59);

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        validateEndDate(startDate, endDate);
        this.startDate = LocalDateTime.of(startDate, startTime);
        this.endDate = LocalDateTime.of(endDate, endTime);
    }

    private static void validateEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ExceptionMessage.START_DATE_BEFORE_END_DATE_EXCEPTION.getMessage());
        }
    }
}
