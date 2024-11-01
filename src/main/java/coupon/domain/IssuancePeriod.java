package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final IssuancePeriod that)) {
            return false;
        }
        return Objects.equals(getStartDateTime(), that.getStartDateTime())
               && Objects.equals(getEndDateTime(), that.getEndDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDateTime(), getEndDateTime());
    }
}
