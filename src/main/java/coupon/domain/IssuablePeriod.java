package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuablePeriod {

    @Column(nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime startAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime endAt;

    public IssuablePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        validate(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validate(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작일이 종료일보다 이후일 수 없습니다.");
        }
    }
}
