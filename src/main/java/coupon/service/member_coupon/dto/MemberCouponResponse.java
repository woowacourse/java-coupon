package coupon.service.member_coupon.dto;

import coupon.domain.member_coupon.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponResponse(
        long memberCouponId,
        long couponId,
        String couponName,
        String category,
        boolean isUsed,
        LocalDate issuedAt,
        LocalDate expiredAt
) {

    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        return new MemberCouponResponse(memberCoupon.getId(), memberCoupon.getCouponId(), memberCoupon.getCouponName(),
                memberCoupon.getCoupon().getCategory().getTypeName(), memberCoupon.isUsed(), memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt());
    }
}
