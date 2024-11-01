package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDate;

public record MemberCouponResponse(
        long memberCouponId,
        long memberId,
        boolean isUsed,
        LocalDate issueDate,
        long couponId,
        String name,
        long minimumAmount,
        long discountAmount,
        LocalDate startIssueDate,
        LocalDate endIssueDate,
        String categoryName
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssueDate(),
                coupon.getId(),
                coupon.getName(),
                coupon.getMinimumAmount(),
                coupon.getDiscountAmount(),
                coupon.getStartIssueDate(),
                coupon.getEndIssueDate(),
                coupon.getCategory().getName()
        );
    }
}
