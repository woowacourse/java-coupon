package coupon.domain;

import coupon.exception.CouponException;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class CouponPeriod {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public CouponPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new CouponException("종료일이 시작일보다 이전입니다.");
        }

        if (!isStartTimeValid(startDate)) {
            throw new CouponException("시작일의 시간은 00:00:00.000000 이어야 합니다.");
        }

        if (!isEndTimeValid(endDate)) {
            throw new CouponException("종료일의 시간은 23:59:59.999999 이어야 합니다.");
        }
    }

    private boolean isStartTimeValid(LocalDateTime startDate) {
        return startDate.toLocalTime().equals(LocalTime.MIDNIGHT);
    }

    private boolean isEndTimeValid(LocalDateTime endDate) {
        return endDate.toLocalTime().equals(LocalTime.of(23, 59, 59, 999999999));
    }
}
