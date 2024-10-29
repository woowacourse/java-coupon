package coupon.coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Coupon {

    private static final int ENABLE_USE_DAYS = 7;

    private final Long id;
    private final CouponName name;
    private final CouponMinimumOrderAmount minimumOrderAmount;
    private final CouponDiscountAmount DiscountAmount;
    private final ProductionCategory productionCategory;
    private final IssuancePeriod issuancePeriod;

    public static LocalDateTime calculateCouponExpiredAt(final LocalDateTime current) {
        return current.plusDays(ENABLE_USE_DAYS);
    }

    public static Coupon create(
            final String name,
            final int minimumOrderAmount,
            final int discountAmount,
            final ProductionCategory productionCategory,
            final LocalDateTime couponStartDate,
            final LocalDateTime couponEndDate
    ) {
        final CouponMinimumOrderAmount couponMinimumOrderAmount = new CouponMinimumOrderAmount(minimumOrderAmount);
        return new Coupon(
                null,
                new CouponName(name),
                couponMinimumOrderAmount,
                new CouponDiscountAmount(discountAmount, couponMinimumOrderAmount),
                productionCategory,
                new IssuancePeriod(couponStartDate, couponEndDate)
        );
    }

    private Coupon(
            final Long id,
            final CouponName name,
            final CouponMinimumOrderAmount minimumOrderAmount,
            final CouponDiscountAmount DiscountAmount,
            final ProductionCategory productionCategory,
            final IssuancePeriod issuancePeriod
    ) {
        this.id = id;
        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
        this.DiscountAmount = DiscountAmount;
        this.productionCategory = productionCategory;
        this.issuancePeriod = issuancePeriod;
    }

    public String nameValue() {
        return name.getValue();
    }

    public int minimumOrderAmountValue() {
        return minimumOrderAmount.getValue();
    }

    public int discountAmountValue() {
        return DiscountAmount.getValue();
    }

    public LocalDateTime couponStartDateValue() {
        return issuancePeriod.getStartDate();
    }

    public LocalDateTime couponEndDateValue() {
        return issuancePeriod.getEndDate();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof final Coupon coupon)) {
            return false;
        }
        return Objects.equals(name, coupon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
