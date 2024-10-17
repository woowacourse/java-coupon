package coupon.domain.coupon;

import java.time.LocalDate;

public class PeriodOfIssuance {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public PeriodOfIssuance(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            return;
        }
        throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
    }
}
