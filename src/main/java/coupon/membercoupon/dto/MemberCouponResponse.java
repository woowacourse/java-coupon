package coupon.membercoupon.dto;

import coupon.membercoupon.domain.MemberCoupon;

import java.time.LocalDate;

public record MemberCouponResponse(long id,
                                   long couponId,
                                   long memberId,
                                   boolean couponUsage,
                                   LocalDate issuanceDate,
                                   LocalDate expirationDate) {

    public static MemberCouponResponse from(MemberCoupon issuedMemberCoupon) {
        return new MemberCouponResponse(
                issuedMemberCoupon.getId(),
                issuedMemberCoupon.getCoupon().getId(),
                issuedMemberCoupon.getMemberId(),
                issuedMemberCoupon.getUsageStatus().isUse(),
                issuedMemberCoupon.getIssuanceDate(),
                issuedMemberCoupon.getExpirationDate()
        );
    }
}
