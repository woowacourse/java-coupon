package coupon.membercoupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public record ExpirationDate(LocalDate date) {

    public boolean isNotExpiate(final LocalDateTime dateTime) {
        // TODO: 구리다.
        final LocalDateTime nextDay = date.plusDays(1).atStartOfDay();
        if (dateTime.isBefore(nextDay)) {
            return true;
        }

        return false;
    }
}
