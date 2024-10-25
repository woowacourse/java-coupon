package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeriodOfIssuance {

    private LocalDate startDate;
    private LocalDate endDate;

    public PeriodOfIssuance(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        validateNotNull(startDate, endDate);
        validateOrder(startDate, endDate);
    }

    private void validateNotNull(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("날짜 정보가 존재하지 않습니다.");
        }
    }

    private void validateOrder(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
    }
}
