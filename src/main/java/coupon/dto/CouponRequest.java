package coupon.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDate;

public record CouponRequest(
        String name,
        Long minimumAmount,
        Long discountAmount,
        Long categoryId,
        LocalDate startIssueDate,
        LocalDate endIssueDate
) {
    public Coupon toEntity(Category category) {
        return new Coupon(name, minimumAmount, discountAmount, startIssueDate, endIssueDate, category);
    }
}
