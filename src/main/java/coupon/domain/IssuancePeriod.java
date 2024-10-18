package coupon.domain;

import java.time.LocalDate;

public class IssuancePeriod {

    private final LocalDate start;
    private final LocalDate end;

    public IssuancePeriod(LocalDate start, LocalDate end) {
        validateAfterOrEqual(start, end);
        this.start = start;
        this.end = end;
    }

    private void validateAfterOrEqual(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("발급 기간의 시작일은 종료일보다 앞설 수 없습니다.");
        }
    }

    public boolean isInRange(LocalDate date) {
        return isAfterOrEqualToStart(date) && isBeforeOrEqualToEnd(date);
    }

    private boolean isAfterOrEqualToStart(LocalDate date) {
        return date.isAfter(start) || date.isEqual(start);
    }

    private boolean isBeforeOrEqualToEnd(LocalDate date) {
        return date.isBefore(end) || date.isEqual(end);
    }
}
