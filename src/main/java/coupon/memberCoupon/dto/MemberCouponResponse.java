package coupon.memberCoupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.memberCoupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long couponId, String couponName, Long memberCouponId,
                                   boolean isUsed, LocalDateTime createdAt, LocalDateTime expiredAt) {

    public static MemberCouponResponse create(Coupon coupon, MemberCoupon memberCoupon) {
        return new MemberCouponResponse(coupon.getId(), coupon.getName().getName(),
                memberCoupon.getId(), memberCoupon.isUsed(), memberCoupon.getCreatedAt(), memberCoupon.getExpiredAt());
    }
}
