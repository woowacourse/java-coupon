package coupon.dto;

import coupon.domain.Coupon;
import java.time.LocalDate;

public record CouponResponse(
        Long id,
        String name,
        Long minimumAmount,
        Long discountAmount,
        LocalDate startIssueDate,
        LocalDate endIssueDate,
        String categoryName
) {
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
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
