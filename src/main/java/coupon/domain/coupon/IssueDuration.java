package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class IssueDuration {
    private static int DEFAULT_DURATION_DAYS = 7;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name= "end_time")
    private LocalDateTime endTime;

    public IssueDuration(LocalDateTime startTime, LocalDateTime endTime) {
        validateTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IssueDuration() {
        this(LocalDateTime.now(), LocalDateTime.now().plusDays(DEFAULT_DURATION_DAYS));
    }

    private void validateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다");
        }
    }
}
