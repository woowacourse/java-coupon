package coupon.coupon.dto;

import coupon.coupon.entity.MemberCouponEntity;

import java.time.LocalDate;

public record MemberCouponResponse(Long id, CouponResponse couponResponse, Long memberId, boolean used,
                                   LocalDate issuedDate, LocalDate expiryDate) {

    public static MemberCouponResponse of(MemberCouponEntity memberCoupon, CouponResponse couponResponse) {
        return new MemberCouponResponse(memberCoupon.getId(), couponResponse, memberCoupon.getMemberId(),
                memberCoupon.isUsed(), memberCoupon.getIssuedDate(), memberCoupon.getExpiryDate());
    }
}
