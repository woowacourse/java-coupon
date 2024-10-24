package coupon.dto;

import coupon.entity.Category;
import coupon.entity.Coupon;
import coupon.entity.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponResponse(
        boolean used,
        LocalDate start,
        LocalDate end,
        String couponName,
        int discount,
        int minimumOrder,
        Category category,
        LocalDate couponStart,
        LocalDate couponEnd
) {
    public static MemberCouponResponse from(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.isUsed(),
                memberCoupon.getStart(),
                memberCoupon.getEnd(),

                coupon.getName(),
                coupon.getDiscount(),
                coupon.getMinimumOrder(),
                coupon.getCategory(),
                coupon.getStart(),
                coupon.getEnd()
        );
    }
}
