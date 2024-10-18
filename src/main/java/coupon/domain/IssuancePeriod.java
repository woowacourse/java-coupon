package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class IssuancePeriod {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public IssuancePeriod(final LocalDate startDate, final LocalDate endDate) {
        validate(startDate, endDate);
        this.startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        this.endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
    }

    private void validate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
