package coupon.domain.coupon;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class IssuancePeriod {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public IssuancePeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
        validateDateIsNull(startDate, endDate);
        validateEndDateIsBeforeThenStartDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDateIsNull(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일 혹은 종료일은 null 값을 입력할 수 없습니다.");
        }
    }

    private void validateEndDateIsBeforeThenStartDate(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일이 시작일보다 이전일 수 없습니다. - " + startDate + ", " + endDate);
        }
    }
}
