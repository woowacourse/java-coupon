package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuancePeriod {

    @Column(name = "startAt", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime startAt;

    @Column(name = "endAt", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime endAt;

    public IssuancePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        validate(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validate(LocalDateTime startAt, LocalDateTime endAt) {
        validateStartAtBeforeEndAt(startAt, endAt);
    }

    private void validateStartAtBeforeEndAt(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
