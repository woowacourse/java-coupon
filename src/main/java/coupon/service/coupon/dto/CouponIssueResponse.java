package coupon.service.coupon.dto;

import coupon.domain.member_coupon.MemberCoupon;
import java.time.LocalDate;

public record CouponIssueResponse(
        long issuedCouponId,
        String couponName,
        LocalDate couponExpiredAt
) {

    public static CouponIssueResponse from(MemberCoupon memberCoupon) {
        return new CouponIssueResponse(memberCoupon.getId(), memberCoupon.getCouponName(), memberCoupon.getExpiredAt());
    }
}
