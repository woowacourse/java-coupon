package coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Duration {

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public Duration(LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateIsNull(Object value, String exceptionMessage) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        validateIsNull(startDate, "발급 시작일은 반드시 존재해야 합니다.");
        validateIsNull(endDate, "발급 종료일은 반드시 존재해야 합니다.");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일보다 이후여야 합니다.");
        }
    }
}
