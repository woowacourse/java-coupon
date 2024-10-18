package coupon.coupon.domain;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.coupon.CouponException;

@Embeddable
public class Term {

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate endAt;

    protected Term() {
    }

    public Term(LocalDate startAt, LocalDate endAt) {
        validateTerm(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateTerm(LocalDate startAt, LocalDate endAt) {
        if (endAt.isBefore(startAt)) {
            throw new CouponException("종료일이 시작일보다 앞설 수 없습니다.");
        }
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }
}
