package coupon.memberCoupon.dto;

import coupon.memberCoupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long couponId, Long memberCouponId, boolean isUsed,
                                   LocalDateTime createdAt, LocalDateTime expiredAt) {

    public static MemberCouponResponse create(Long couponId, MemberCoupon memberCoupon) {
        return new MemberCouponResponse(couponId, memberCoupon.getId(), memberCoupon.isUsed(),
                memberCoupon.getCreatedAt(), memberCoupon.getExpiredAt());
    }
}
