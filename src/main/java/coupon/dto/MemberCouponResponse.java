package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponResponse(
        Long memberCouponId,
        Long couponId,
        boolean used,
        String name,
        Long discountAmount,
        Long minimumAmount,
        String category,
        LocalDate usedDate,
        LocalDate expiredDate,
        LocalDate startDate,
        LocalDate endDate
) {
    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                coupon.getId(),
                memberCoupon.isUsed(),
                coupon.getName(),
                coupon.getDiscountAmount().longValue(),
                coupon.getMinimumAmount().longValue(),
                coupon.getCategory().name(),
                memberCoupon.getUsedDate(),
                memberCoupon.getExpiredDate(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }
}
