
package coupon.coupon.domain;

import java.time.LocalDate;
import coupon.coupon.CouponException;

public class Term {

    private final LocalDate startAt;
    private final LocalDate endAt;

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
}
