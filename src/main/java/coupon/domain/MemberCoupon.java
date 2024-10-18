package coupon.domain;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MemberCoupon {

    private final Long id;
    private final Long couponId;
    private final Long memberId;
    private final boolean used;
    private final LocalDate issueDate;
    private final LocalDate expirationDate;

    public MemberCoupon(Long id, Long couponId, Long memberId, boolean used, LocalDate issueDate) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = used;
        this.issueDate = issueDate;
        this.expirationDate = issueDate.plusDays(6);
    }
}
