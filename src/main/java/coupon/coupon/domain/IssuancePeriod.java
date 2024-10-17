package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record IssuancePeriod(LocalDate startDate, LocalDate endDate) {

    public boolean canIssue(final LocalDateTime dateTime) {
        LocalDateTime issuedStartDateTime = startDate.atStartOfDay();
        LocalDateTime issuedEndDateTime = endDate.atStartOfDay().plusDays(1);

        // TODO: 구리다.
        boolean isEqualOrAfterThanStartDateTime = dateTime.isEqual(issuedStartDateTime) || dateTime.isAfter(issuedStartDateTime);
        if (isEqualOrAfterThanStartDateTime && dateTime.isBefore(issuedEndDateTime)) {
            return true;
        }

        return false;
    }
}

