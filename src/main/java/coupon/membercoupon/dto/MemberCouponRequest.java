package coupon.membercoupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;

import java.time.LocalDate;

public record MemberCouponRequest(long couponId, long memberId, LocalDate issuanceDate) {

    public MemberCoupon toEntity(Coupon coupon) {
        return new MemberCoupon(coupon, memberId, issuanceDate);
    }
}
