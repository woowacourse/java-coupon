package coupon.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(
        Long id,
        String couponName,
        Integer discountAmount,
        Integer minimumOrderAmount,
        Category category,
        Long memberId,
        Boolean isUsed,
        LocalDateTime issuedDateTime,
        LocalDateTime limitDateTime
) {
    public static MemberCouponResponse of(Coupon coupon, MemberCoupon memberCoupon) {
        return new MemberCouponResponse(memberCoupon.getId(), coupon.getName(), coupon.getDiscountAmount(),
                coupon.getMinimumOrderAmount(), coupon.getCategory(), memberCoupon.getMemberId(),
                memberCoupon.getIsUsed(), memberCoupon.getIssuedDateTime(), memberCoupon.getLimitDateTime());
    }
}
