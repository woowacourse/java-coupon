package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuancePeriod {

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime endDate;

    public IssuancePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일이 종료일보다 이후일 수 없습니다.");
        }
    }
}
