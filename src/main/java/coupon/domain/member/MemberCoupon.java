package coupon.domain.member;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.type.descriptor.DateTimeUtils;


@Getter(AccessLevel.PACKAGE)
public class MemberCoupon {
    public static final int VALID_DAYS_SINCE_ISSUED = 7;
    private final long id;
    private final long couponId;
    private final long memberId;
    private final boolean isUsed;
    private final LocalDateTime issueDate;
    private final LocalDateTime expirationDate;

    public MemberCoupon(long id, long couponId, long memberId, boolean isUsed, LocalDateTime issueDate,
                        LocalDateTime expirationDate) {
        this.id = id;
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
