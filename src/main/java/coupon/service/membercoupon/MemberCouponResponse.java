package coupon.service.membercoupon;

import coupon.entity.coupon.Coupon;
import coupon.entity.membercoupon.MemberCoupon;
import coupon.service.coupon.CouponResponse;
import java.time.LocalDate;

public record MemberCouponResponse(Long id,
                                   boolean isUsed,
                                   LocalDate issuedAt,
                                   LocalDate expiredDate,
                                   CouponResponse couponResponse) {

    public MemberCouponResponse(MemberCoupon memberCoupon, Coupon coupon) {
        this(memberCoupon.getId(),
                memberCoupon.isUsed(),
                memberCoupon.getCouponPeriod().getIssuedAt(),
                memberCoupon.getCouponPeriod().getExpiredAt(),
                new CouponResponse(coupon)
        );
    }
}
