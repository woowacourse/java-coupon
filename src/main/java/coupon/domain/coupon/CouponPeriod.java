package coupon.domain.coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CouponPeriod {

    private final LocalDate startDate;

    private final LocalDate endDate;

    public CouponPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean canAddToMember(LocalDateTime base) {
        LocalDateTime startDateTimeExclude = LocalDateTime.of(
                startDate.minusDays(1),
                LocalTime.of(23, 59, 59, 999999999)
        );
        LocalDateTime endDateTimeExclude = LocalDateTime.of(
                endDate.plusDays(1),
                LocalTime.of(0, 0, 0, 0)
        );
        return base.isAfter(startDateTimeExclude) && base.isBefore(endDateTimeExclude);
    }
}
