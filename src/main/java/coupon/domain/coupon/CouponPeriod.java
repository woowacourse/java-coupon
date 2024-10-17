package coupon.domain.coupon;

import java.time.LocalDateTime;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponPeriod {

    @Column(name = "start_date", columnDefinition = "DATETIME(6)")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "DATETIME(6)")
    private LocalDateTime endDate;

    public CouponPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validateCouponDateRange(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateCouponDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new CouponException(ExceptionType.COUPON_DATE_INVALID);
        }
    }
}
