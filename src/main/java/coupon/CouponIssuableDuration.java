package coupon;

import java.time.LocalDate;

class CouponIssuableDuration {

    private final LocalDate start;
    private final LocalDate end;

    public CouponIssuableDuration(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        this.start = start;
        this.end = end;
    }
}
