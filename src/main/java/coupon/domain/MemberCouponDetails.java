package coupon.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberCouponDetails {

    private final Long id;
    private final Coupon coupon;
    private final Member member;
    private final boolean isUsed;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiredAt;

    public MemberCouponDetails(MemberCoupon memberCoupon, Coupon coupon) {
        this.id = memberCoupon.getId();
        this.coupon = coupon;
        this.member = memberCoupon.getMember();
        this.isUsed = memberCoupon.isUsed();
        this.issuedAt = memberCoupon.getIssuedAt();
        this.expiredAt = memberCoupon.getExpiredAt();
    }

    public boolean isNotEmpty() {
        return coupon != null;
    }
}
