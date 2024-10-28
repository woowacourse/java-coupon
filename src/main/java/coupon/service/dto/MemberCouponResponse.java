package coupon.service.dto;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.MemberCoupon;

import java.time.LocalDateTime;

public record MemberCouponResponse(
        Long memberCouponId,
        Long memberId,
        boolean isUsed,
        LocalDateTime issuedAt,
        LocalDateTime expireAt,
        Coupon coupon
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(memberCoupon.getId(), memberCoupon.getMember().getId(), memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(), memberCoupon.getExpireAt(), coupon);
    }
}
