package coupon.coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Coupon {

    // TODO : 쿠폰의 만료 기간을 각 쿠폰이 필드로 가지도록 개선
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

    public String getNameValue() {
        return name.getValue();
    }

    public int getMinimumOrderAmountValue() {
        return minimumOrderAmount.getValue();
    }

    public int getDiscountAmountValue() {
        return DiscountAmount.getValue();
    }

    public ProductionCategory getProductionCategory() {
        return productionCategory;
    }

    public LocalDateTime getCouponStartDateValue() {
        return issuancePeriod.getStartDate();
    }

    public LocalDateTime getCouponEndDateValue() {
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

    public Long getId() {
        return id;
    }
}
