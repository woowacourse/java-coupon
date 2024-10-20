package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class IssueDuration {

    private LocalDateTime startAt;

    @Column(name= "end_at")
    private LocalDateTime endAt;

    public IssueDuration(LocalDateTime startAt, LocalDateTime endAt) {
        validate(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    protected IssueDuration() {
    }

    private void validate(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작 시점은 종료 시점보다 이전이어야 합니다");
        }
    }
}
