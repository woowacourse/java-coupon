package coupon.domain;

import coupon.exception.InvalidCouponDiscountAmount;
import lombok.Getter;

@Getter
public class DiscountAmount {

    private final long value;

    public DiscountAmount(final long value) {
        validateAmount(value);
        this.value = value;
    }

    private void validateAmount(final long value) {
        if (value < 1000 || value > 10000 || value % 500 != 0) {
            throw new InvalidCouponDiscountAmount("할인 금액이 올바르지 않습니다.");
        }
    }
}
