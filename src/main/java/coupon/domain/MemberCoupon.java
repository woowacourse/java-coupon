package coupon.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberCoupon {

    private static final int MAX_ISSUE_COUNT = 5;

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

    public static MemberCoupon issue(Long couponId, Member member, int issuedCouponCount) {
        if (issuedCouponCount >= MAX_ISSUE_COUNT) {
            throw new IllegalArgumentException("최대 발급 수량을 초과했습니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        return new MemberCoupon(couponId, member, false, now, now.plusDays(7));
    }
}
