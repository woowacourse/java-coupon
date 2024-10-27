package coupon.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;

public record CouponRequest(
        String name,
        Integer discountAmount,
        Integer minimumOrderAmount,
        Category category
) {
    public Coupon toCoupon() {
        return new Coupon(name(), discountAmount(), minimumOrderAmount(), category());
    }
}
