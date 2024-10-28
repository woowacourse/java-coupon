package coupon.domain.member;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.type.descriptor.DateTimeUtils;


@Getter
@ToString
@AllArgsConstructor
public class MemberCoupon {
    private static final int VALID_DAYS_SINCE_ISSUED = 7;

    private long id;
    private final long couponId;
    private final long memberId;
    private final boolean isUsed;
    private final LocalDateTime issueDate;
    private final LocalDateTime expirationDate;

    public MemberCoupon(long couponId, long memberId, boolean isUsed, LocalDateTime issueDate,
                        LocalDateTime expirationDate) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return issueDate.toLocalDate().until(expirationDate.toLocalDate()).getDays() + 1 > VALID_DAYS_SINCE_ISSUED;
    }
}
