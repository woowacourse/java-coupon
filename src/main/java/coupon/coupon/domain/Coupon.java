package coupon.coupon.domain;

import java.time.LocalDateTime;

public class Coupon {

    private final CouponName name;
    private final DiscountPrice discountPrice;
    private final MinimumOrderPrice minimumOrderPrice;
    private final DiscountPercent discountPercent;
    private final Category category;
    private final IssuePeriod issuedPeriod;

    public Coupon(
            CouponName name,
            DiscountPrice discountPrice,
            MinimumOrderPrice minimumOrderPrice,
            DiscountPercent discountPercent,
            Category category,
            IssuePeriod issuedPeriod
    ) {
        this.name = name;
        this.discountPrice = discountPrice;
        this.minimumOrderPrice = minimumOrderPrice;
        this.discountPercent = discountPercent;
        this.category = category;
        this.issuedPeriod = issuedPeriod;
    }

    public String getName() {
        return name.name();
    }

    public int getDiscountPrice() {
        return discountPrice.price();
    }

    public int getMinimumOrderPrice() {
        return minimumOrderPrice.price();
    }

    public double getDiscountPercent() {
        return discountPercent.getPercent();
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getIssuedAt() {
        return issuedPeriod.issuedAt();
    }

    public LocalDateTime getExpiresAt() {
        return issuedPeriod.expiresAt();
    }
}
