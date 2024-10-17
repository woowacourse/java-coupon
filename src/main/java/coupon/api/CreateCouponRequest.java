package coupon.api;

import java.time.LocalDate;
import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponIssuableDuration;
import coupon.domain.CouponName;

public record CreateCouponRequest(
        String name,
        String category,
        String discountAmount,
        String applicableAmount,
        LocalDate start,
        LocalDate end
) {

    public Coupon toCoupon() {
        CouponName name = new CouponName(this.name);
        CouponCategory category = CouponCategory.valueOf(this.category.toUpperCase());
        CouponIssuableDuration issuableDuration = new CouponIssuableDuration(start, end);

        return new Coupon(name, category, issuableDuration, "1000", "10000");
    }
}
