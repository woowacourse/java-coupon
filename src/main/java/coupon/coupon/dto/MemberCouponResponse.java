package coupon.coupon.dto;

import coupon.coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(
        long id,
        CouponResponse couponResponse,
        long memberId,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, CouponResponse couponResponse) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                couponResponse,
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt()
        );
    }
}
