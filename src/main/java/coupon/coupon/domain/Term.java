package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.CouponException;

@Embeddable
public class Term {

    public static final String TERM_DATE_NULL_MESSAGE = "시작 날짜와 끝 날짜를 설정해주세요";
    private static final String TERM_MESSAGE = "종료일이 시작일보다 앞설 수 없습니다.";

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    protected Term() {
    }

    public Term(LocalDate startAt, LocalDate endAt) {
        validateNull(startAt, endAt);
        validateTerm(startAt, endAt);
        this.startAt = startAt.atTime(0, 0, 0, 0);
        this.endAt = endAt.atTime(23, 59, 59, 999_999_000);
    }

    private void validateNull(LocalDate startAt, LocalDate endAt) {
        if (Objects.isNull(startAt) || Objects.isNull(endAt)) {
            throw new CouponException(TERM_DATE_NULL_MESSAGE);
        }
    }

    private void validateTerm(LocalDate startAt, LocalDate endAt) {
        if (endAt.isBefore(startAt)) {
            throw new CouponException(TERM_MESSAGE);
        }
    }

    public boolean doesNotContain(LocalDateTime now) {
        return startAt.isAfter(now) || endAt.isBefore(now);
    }
}
