package coupon.domain;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CouponIssuableDuration(
        @Column(name = "issuable_start_date", nullable = false) LocalDate start,
        @Column(name = "issuable_end_date", nullable = false) LocalDate end
) {

    public CouponIssuableDuration {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    public boolean isIssuable() {
        LocalDate today = LocalDate.now();

        return !today.isBefore(start) && !today.isAfter(end);
    }
}
