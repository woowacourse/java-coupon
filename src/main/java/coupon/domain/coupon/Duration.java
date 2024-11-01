package coupon.domain.coupon;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class Duration {
    private final LocalDateTime beginAt;
    private final LocalDateTime endAt;

    public Duration(LocalDate from, LocalDate to) {
        beginAt = LocalDateTime.of(from, LocalTime.MIN);
        endAt = LocalDateTime.of(to, LocalTime.MIN);
        validate(beginAt, endAt);
    }

    private void validate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new DateTimeException("start date must be after end date");
        }
    }

    boolean isIssuable(LocalDateTime target) {
        return inRange(target);
    }

    boolean isIssuableNow() {
        return inRange(LocalDateTime.now());
    }

    private boolean inRange(LocalDateTime target) {
        return (target.isEqual(beginAt) || target.isAfter(beginAt))
               && (target.isEqual(endAt) || target.isBefore(endAt));
    }
}
