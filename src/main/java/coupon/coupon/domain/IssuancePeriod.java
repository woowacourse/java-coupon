package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record IssuancePeriod(LocalDate startDate, LocalDate endDate) {

    public boolean canIssue(final LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public LocalDateTime getStartDateTime() {
        return startDate.atStartOfDay();
    }

    public LocalDateTime getEndDateTime() {
        return endDate.atTime(23, 59, 59, 999999999);
    }
}
