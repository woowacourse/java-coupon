package coupon;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class Coupon {

    private Long id;
    private CouponName name;
    private CouponCategory category;
    private CouponIssuableDuration expiryDuration;
    private CouponDiscountApply discountApply;

    public Coupon(
            CouponName name,
            CouponCategory category,
            CouponIssuableDuration expiryDuration,
            String discountAmount,
            String applicableAmount
    ) {
        this(null, name, category, expiryDuration, new CouponDiscountApply(discountAmount, applicableAmount));
    }
}
