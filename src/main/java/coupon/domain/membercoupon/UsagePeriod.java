package coupon.domain.membercoupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UsagePeriod {

    private static final int VALID_DURATION = 7;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    public UsagePeriod(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    private LocalDateTime calculateExpireDate(LocalDateTime issueDate) {
        return issueDate.plusDays(VALID_DURATION).toLocalDate().atTime(23, 59, 59, 999999);
    }
}
