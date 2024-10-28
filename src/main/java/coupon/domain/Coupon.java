package coupon.domain;

import java.util.Objects;

import lombok.Getter;

@Getter
public class Coupon {

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    private final CouponName name;
    private final DiscountAmount discountAmount;
    private final MinOrderAmount minOrderAmount;
    private final IssuancePeriod issuancePeriod;

    public Coupon(final CouponName name, final DiscountAmount discountAmount, final MinOrderAmount minOrderAmount,
                  final IssuancePeriod issuancePeriod) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.issuancePeriod = issuancePeriod;
    }

    public double calculateDiscountRate() {
        final double result = Math.floor((double) discountAmount.getValue() / minOrderAmount.getAmount() * 100);
        if (result < MIN_DISCOUNT_RATE || MAX_DISCOUNT_RATE < result) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %s 이상 %s 이하여야 합니다. 현재 할인율 %s", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE, result));
        }
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Coupon coupon)) {
            return false;
        }
        return Objects.equals(getName(), coupon.getName())
               && Objects.equals(getDiscountAmount(), coupon.getDiscountAmount())
               && Objects.equals(getMinOrderAmount(), coupon.getMinOrderAmount())
               && Objects.equals(getIssuancePeriod(), coupon.getIssuancePeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDiscountAmount(), getMinOrderAmount(), getIssuancePeriod());
    }
}
