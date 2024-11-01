package coupon.service.dto;

import coupon.domain.coupon.Coupon;
import coupon.domain.membercoupon.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(
        Long id,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        CouponResponse coupon
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt(),
                CouponResponse.from(coupon)
        );
    }
}
