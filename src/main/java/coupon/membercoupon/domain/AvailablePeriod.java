package coupon.membercoupon.domain;

import coupon.coupon.domain.IssuedPeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AvailablePeriod {
    private static final int AVAILABLE_END_DATE = 7;

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public AvailablePeriod(IssuedPeriod issuedPeriod) {
        LocalDateTime startDateTime = calculateStartDate(issuedPeriod);
        LocalDateTime endDateTime = calculateEndDate(issuedPeriod);
        validateAvailablePeriod(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateAvailablePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("사용 가능일[%s]과 사용 만료일[%s]이 부적절합니다.".formatted(startDateTime, endDateTime));
        }
    }

    private LocalDateTime calculateStartDate(IssuedPeriod issuedPeriod) {
        LocalDateTime start = issuedPeriod.getStartDateTime();
        LocalDateTime today = LocalDateTime.now();
        if (start.isBefore(today)) {
            return today;
        }
        return start;
    }


    private LocalDateTime calculateEndDate(IssuedPeriod issuedPeriod) {
        LocalDateTime end = issuedPeriod.getEndDateTime();
        LocalDateTime today = LocalDateTime.now();

        LocalDate availableEndDate = today.toLocalDate().plusDays(AVAILABLE_END_DATE);
        if (availableEndDate.isBefore(end.toLocalDate())) {
            return availableEndDate.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).plusNanos(999999000);
        }
        return end;
    }

    public boolean isAvailable() {
        LocalDateTime today = LocalDateTime.now();
        return (today.isAfter(startDateTime) || today.isEqual(startDateTime)) && today.isBefore(endDateTime);
    }
}
