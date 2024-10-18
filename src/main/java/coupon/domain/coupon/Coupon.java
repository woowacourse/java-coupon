package coupon.domain.coupon;

import coupon.domain.Category;
import lombok.Getter;

@Getter
public class Coupon {

    private final CouponName couponName;
    private final DiscountMount discountMount;
    private final MinimumMount minimumMount;
    private final Category category;
    private final Period period;

    public Coupon(CouponName couponName, DiscountMount discountMount, MinimumMount minimumMount, Category category,
                  Period period) {
        this.couponName = couponName;
        this.discountMount = discountMount;
        this.minimumMount = minimumMount;
        this.category = category;
        this.period = period;
    }
}
