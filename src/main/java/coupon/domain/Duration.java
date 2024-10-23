package coupon.domain;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Duration {

    private LocalDate startDate;
    private LocalDate endDate;

    public Duration(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
