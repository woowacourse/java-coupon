package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TimeManager {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeManager(LocalDate from, LocalDate to) {
        start = LocalDateTime.of(from, LocalTime.of(0, 0, 0));
        end = LocalDateTime.of(from, LocalTime.of(23, 59, 59, 999999));
    }

    boolean isIssuable() {
        return start.isBefore(end) || start.isEqual(end);
    }
}
