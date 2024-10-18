package coupon.domain.usercoupon;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserCoupon {

    private final Long id;
    private final Long userId;
    private final Long couponId;
    private final boolean isUsed;
    private final UsingDuration usingDuration;

    public UserCoupon(Long id, Long userId, Long couponId, boolean isUsed, LocalDateTime issuedDateTime) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.isUsed = isUsed;
        this.usingDuration = new UsingDuration(issuedDateTime);
    }
}
