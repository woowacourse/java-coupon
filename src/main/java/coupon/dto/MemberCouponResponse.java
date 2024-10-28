package coupon.dto;

import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long id, LocalDateTime issuedAt, LocalDateTime expiredAt, String memberName, String couponName) {

    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        return new MemberCouponResponse(memberCoupon.getId(), memberCoupon.getIssuedAt(), memberCoupon.getExpiredAt(),
                memberCoupon.getMember().getName(), memberCoupon.getCoupon().getName());
    }
}
