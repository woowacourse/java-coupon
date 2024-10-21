package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class IssuedPeriod {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public IssuedPeriod(LocalDateTime startDate, LocalDateTime endDateTime) {
        validateLocalDateTime(startDate, endDateTime);
        this.startDateTime = startDate;
        this.endDateTime = endDateTime;
    }

    public IssuedPeriod(LocalDate today) {
        this(today.atStartOfDay(),
                today.atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).plusNanos(999999000));
    }

    private void validateLocalDateTime(LocalDateTime issuedDateTime, LocalDateTime expiredDateTime) {
        if (expiredDateTime.isBefore(issuedDateTime)) {
            throw new IllegalArgumentException("시작일은 종료일 보다 이전이어야 합니다.");
        }
    }
}
