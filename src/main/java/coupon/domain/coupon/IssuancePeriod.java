package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuancePeriod {

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    public IssuancePeriod(LocalDate startDate, LocalDate endDate) {
        validateDateTimes(startDate, endDate);
        this.startAt = LocalDateTime.of(startDate, LocalTime.MIN);
        this.endAt = LocalDateTime.of(endDate, LocalTime.MAX);
    }

    private void validateDateTimes(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            String message = "시작일은 종료일보다 이전이어야 합니다.";
            throw new IllegalArgumentException(message);
        }
    }
}
