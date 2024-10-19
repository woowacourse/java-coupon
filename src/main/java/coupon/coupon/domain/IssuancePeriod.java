package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record IssuancePeriod(LocalDate startDate, LocalDate endDate) {

    public boolean canIssue(final LocalDateTime dateTime) {
        LocalDateTime startDateTime = getStartDateTime();
        LocalDateTime endDateTime = getEndDateTime();
        return !dateTime.isBefore(startDateTime) && !dateTime.isAfter(endDateTime);
    }

    public LocalDateTime getStartDateTime() {
        return startDate.atStartOfDay();
    }

    public LocalDateTime getEndDateTime() {
        return endDate.atTime(LocalTime.MAX);
    }
}
