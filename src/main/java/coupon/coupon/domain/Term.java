package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.coupon.CouponException;

@Embeddable
public class Term {

    private static final String TERM_MESSAGE = "종료일이 시작일보다 앞설 수 없습니다.";

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    protected Term() {
    }

    public Term(LocalDate startAt, LocalDate endAt) {
        this(startAt.atTime(0, 0, 0, 0), endAt.atTime(23, 59, 59, 999_999_000));
    }

    private Term(LocalDateTime startAt, LocalDateTime endAt) {
        validateTerm(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateTerm(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new CouponException(TERM_MESSAGE);
        }
    }
}
