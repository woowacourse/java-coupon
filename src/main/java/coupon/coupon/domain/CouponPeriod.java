package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponPeriod {

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    public CouponPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validateEndOverStartRange(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateEndOverStartRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("쿠폰의 시작일은 종료일 이전이어야 해요.");
        }
    }
}
