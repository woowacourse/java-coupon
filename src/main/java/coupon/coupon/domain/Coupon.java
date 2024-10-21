package coupon.coupon.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
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
        return name.getName();
    }

    public int getDiscountPrice() {
        return discountPrice.getPrice();
    }

    public int getMinimumOrderPrice() {
        return minimumOrderPrice.getPrice();
    }

    public double getDiscountPercent() {
        return discountPercent.getPercent();
    }

    public LocalDateTime getIssuedAt() {
        return issuedPeriod.getIssuedAt();
    }

    public LocalDateTime getExpiresAt() {
        return issuedPeriod.getExpiresAt();
    }
}
