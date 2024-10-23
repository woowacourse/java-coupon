package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class IssuePeriod {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public IssuePeriod(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new CouponException(ErrorConstant.COUPON_ISSUE_DATE_IS_NULL);
        }
        if (startDate.isAfter(endDate)) {
            throw new CouponException(ErrorConstant.NOT_AVAILABLE_COUPON_DATE);
        }
    }
}
