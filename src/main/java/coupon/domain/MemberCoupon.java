package coupon.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberCoupon {

    private final Long id;
    private final Long couponId;
    private final Member member;
    private final boolean isUsed;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiredAt;

    public MemberCoupon(Long couponId, Member member, boolean isUsed, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.id = null;
        this.couponId = couponId;
        this.member = member;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static MemberCoupon issue(Long couponId, Member member) {
        LocalDateTime now = LocalDateTime.now();
        return new MemberCoupon(couponId, member, false, now, now.plusDays(7));
    }
}
