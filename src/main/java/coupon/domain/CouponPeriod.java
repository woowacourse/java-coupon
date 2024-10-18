package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CouponPeriod {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CouponPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new CouponException("종료일이 시작일보다 이전입니다.");
        }
    }
}
