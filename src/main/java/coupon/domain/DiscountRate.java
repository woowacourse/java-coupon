package coupon.domain;

import coupon.exception.InvalidCouponDiscountRate;
import lombok.Getter;

@Getter
public class DiscountRate {

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
        if (value < 3 || value > 20) {
            throw new InvalidCouponDiscountRate("할인 비율이 올바르지 않습니다.");
        }
    }
}
