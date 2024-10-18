package coupon.domain.coupon;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class IssuancePeriod {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public IssuancePeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
        validateStartDate(startDate, endDate);
        validateEndDate(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateStartDate(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("시작일로 null 값을 입력할 수 없습니다.");
        }

        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다. - " + startDate + ", " + endDate);
        }
    }

    private void validateEndDate(final LocalDateTime endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("종료일로 null 값을 입력할 수 없습니다.");
        }
    }
}
