package coupon.membercoupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponResponse;
import coupon.member.domain.Member;
import coupon.member.dto.MemberResponse;
import coupon.membercoupon.domain.MemberCoupon;

import java.time.LocalDate;

public record MemberCouponResponse(long id,
                                   CouponResponse couponResponse,
                                   MemberResponse memberResponse,
                                   boolean couponUsage,
                                   LocalDate issuanceDate,
                                   LocalDate expirationDate) {

    public static MemberCouponResponse of(MemberCoupon issuedMemberCoupon, Coupon coupon, Member member) {
        return new MemberCouponResponse(
                issuedMemberCoupon.getId(),
                CouponResponse.from(coupon),
                MemberResponse.from(member),
                issuedMemberCoupon.getUsageStatus().isUse(),
                issuedMemberCoupon.getIssuanceDate(),
                issuedMemberCoupon.getExpirationDate()
        );
    }
}
