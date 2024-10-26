package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record IssuancePeriod(LocalDate startDate, LocalDate endDate) {

    public IssuancePeriod {
        validateIssuancePeriodNull(startDate, endDate);
    }

    public boolean canIssue(final LocalDateTime dateTime) {
        LocalDateTime startDateTime = getStartDateTime();
        LocalDateTime endDateTime = getEndDateTime();
        return !dateTime.isBefore(startDateTime) && !dateTime.isAfter(endDateTime);
    }

    private void validateIssuancePeriodNull(final LocalDate startDate, final LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("쿠폰 발행 시작일 또는 종료일은 null이 될 수 없습니다.");
        }
    }

    public LocalDateTime getStartDateTime() {
        return startDate.atStartOfDay();
    }

    public LocalDateTime getEndDateTime() {
        return endDate.atTime(LocalTime.MAX);
    }
}
