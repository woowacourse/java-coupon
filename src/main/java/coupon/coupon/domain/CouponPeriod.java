package coupon.coupon.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponPeriod {

    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public CouponPeriod(LocalDateTime startAt, LocalDateTime endAt) {
        validateEndDateOverStartRange(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateEndDateOverStartRange(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new IllegalArgumentException("쿠폰의 종료일은 시작일 이후이어야 해요.");
        }
    }
}
