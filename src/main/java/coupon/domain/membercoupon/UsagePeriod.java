package coupon.domain.membercoupon;

import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UsagePeriod {

    private static final int VALID_DURATION = 6;

    @Column(name = "issue_date", columnDefinition = "DATETIME(6)", nullable = false)
    private LocalDateTime issueDate;

    @Column(name = "expire_date", columnDefinition = "DATETIME(6)", nullable = false)
    private LocalDateTime expireDate;

    public UsagePeriod(LocalDateTime issueDate) {
        this.issueDate = issueDate;
        this.expireDate = calculateExpireDate(issueDate);
    }

    private LocalDateTime calculateExpireDate(LocalDateTime issueDate) {
        return issueDate.plusDays(VALID_DURATION).with(LocalTime.MAX);
    }
}
