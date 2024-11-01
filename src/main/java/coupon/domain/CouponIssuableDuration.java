package coupon.domain;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponIssuableDuration {

    @Column(name = "issuable_start_date", nullable = false)
    private LocalDate start;

    @Column(name = "issuable_end_date", nullable = false)
    private LocalDate end;

    public CouponIssuableDuration(LocalDate start, LocalDate end) {
        validatePeriod(start, end);

        this.start = start;
        this.end = end;
    }

    private void validatePeriod(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    public boolean isIssuable() {
        LocalDate today = LocalDate.now();

        return !today.isBefore(start) && !today.isAfter(end);
    }
}
