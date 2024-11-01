package coupon.membercoupon.dto;

import coupon.membercoupon.domain.MemberCoupon;

import java.time.LocalDate;

public record MemberCouponRequest(long couponId, long memberId, LocalDate issuanceDate) {

    public MemberCoupon toEntity() {
        return new MemberCoupon(couponId, memberId, issuanceDate);
    }
}
