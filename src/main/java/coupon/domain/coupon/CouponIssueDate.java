package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class CouponIssueDate {
    private LocalDate issueStartDate;
    private LocalDate issueEndDate;

    public CouponIssueDate(LocalDate issueStartDate, LocalDate issueEndDate) {
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }

    public CouponIssueDate() {
    }

    public boolean isDateAvailable(LocalDate date) {
        if (date.isBefore(issueStartDate) || date.isAfter(issueEndDate)) {
            return false;
        }
        return true;
    }
}
