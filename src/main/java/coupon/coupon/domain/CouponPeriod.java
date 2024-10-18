package coupon.coupon.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponPeriod {

    private LocalDateTime startDate;
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
