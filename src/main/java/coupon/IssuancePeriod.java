package coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IssuancePeriod {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public IssuancePeriod(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
