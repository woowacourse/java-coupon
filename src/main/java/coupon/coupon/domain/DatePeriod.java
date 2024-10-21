package coupon.coupon.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DatePeriod {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public DatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validateEndDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ExceptionMessage.START_DATE_BEFORE_END_DATE_EXCEPTION.getMessage());
        }
    }
}
