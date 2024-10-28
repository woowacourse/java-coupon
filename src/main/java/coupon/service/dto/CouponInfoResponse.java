package coupon.service.dto;

import java.time.LocalDateTime;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;

public record CouponInfoResponse(
        String couponName,
        long discountAmount,
        long minimumOrderAmount,
        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean isUsed,
        LocalDateTime issuedDate,
        LocalDateTime expirationDate
) {

    public static CouponInfoResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new CouponInfoResponse(
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getValidityPeriod().getStartDate(),
                coupon.getValidityPeriod().getEndDate(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedDate(),
                memberCoupon.getExpirationDate()
        );
    }
}
