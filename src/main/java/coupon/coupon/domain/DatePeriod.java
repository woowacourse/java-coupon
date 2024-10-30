package coupon.coupon.domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DatePeriod {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        validateEndDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static void validateEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ExceptionMessage.START_DATE_BEFORE_END_DATE_EXCEPTION.getMessage());
        }
    }
}
