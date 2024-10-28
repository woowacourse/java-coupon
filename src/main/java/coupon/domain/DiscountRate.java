package coupon.domain;

import coupon.exception.InvalidCouponDiscountRate;
import lombok.Getter;

@Getter
public class DiscountRate {

    public static final int MIN_VALUE = 3;
    public static final int MAX_VALUE = 20;
    private final long value;

    public DiscountRate(final DiscountAmount discountAmount, final MinimumOrderAmount minimumOrderAmount) {
        long discountRate = calculateRate(discountAmount, minimumOrderAmount);
        validateRate(discountRate);
        this.value = discountRate;
    }

    public DiscountRate(final long value) {
        validateRate(value);
        this.value = value;
    }

    private long calculateRate(final DiscountAmount discountAmount, final MinimumOrderAmount minimumOrderAmount) {
        return discountAmount.getValue() / minimumOrderAmount.getValue();
    }

    private void validateRate(final long value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new InvalidCouponDiscountRate("할인 비율이 올바르지 않습니다.");
        }
    }
}
