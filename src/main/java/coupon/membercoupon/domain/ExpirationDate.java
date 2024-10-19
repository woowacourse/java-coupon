package coupon.membercoupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record ExpirationDate(LocalDate date) {

    public boolean isNotExpiate(final LocalDateTime dateTime) {
        final LocalDateTime expirationDateTime = getEndDateTime();
        return dateTime.isBefore(expirationDateTime);
    }

    public LocalDateTime getEndDateTime() {
        return date.atTime(LocalTime.MAX);
    }
}
