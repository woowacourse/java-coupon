package coupon.domain;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeManager {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeManager(LocalDate from, LocalDate to) {
        start = LocalDateTime.of(from, LocalTime.of(0, 0, 0));
        end = LocalDateTime.of(to, LocalTime.of(23, 59, 59, 999999));
        validate(start, end);
    }

    private void validate(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new DateTimeException("");
        }
    }

    boolean isIssuable(LocalDateTime target) {
        return inRange(target);
    }

    boolean isIssuableNow() {
        return inRange(LocalDateTime.now());
    }

    private boolean inRange(LocalDateTime target) {
        return (target.isEqual(start) || target.isAfter(start))
               && (target.isEqual(end) || target.isBefore(end));
    }
}
