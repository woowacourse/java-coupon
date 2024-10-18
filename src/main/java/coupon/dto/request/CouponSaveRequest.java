package coupon.dto.request;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.DiscountAmount;
import coupon.domain.IssuancePeriod;
import coupon.domain.MinOrderAmount;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CouponSaveRequest(
        @NotNull
        String name,

        @NotNull
        long discountAmount,

        @NotNull
        long minOrderAmount,

        @NotNull
        String category,

        @NotNull
        LocalDateTime issuanceStartDate,

        @NotNull
        LocalDateTime issuanceEndDate
) {

    public Coupon toCoupon() {
        return new Coupon(
                new CouponName(name),
                new DiscountAmount(discountAmount),
                new MinOrderAmount(minOrderAmount),
                Category.from(category),
                new IssuancePeriod(issuanceStartDate, issuanceEndDate)
        );
    }
}
