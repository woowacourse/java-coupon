package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class IssuablePeriod {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public IssuablePeriod(LocalDateTime startTime, LocalDateTime endTime) {
        validateTimes(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateTimes(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
