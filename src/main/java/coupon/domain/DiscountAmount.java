package coupon.domain;

import coupon.exception.InvalidCouponDiscountAmount;
import lombok.Getter;

@Getter
public class DiscountAmount {

    public static final int MIN_VALUE = 1000;
    public static final int MAX_VALUE = 10000;
    public static final int VALUE_UNIT = 500;
    private final long value;

    public DiscountAmount(final long value) {
        validateAmount(value);
        this.value = value;
    }

    private void validateAmount(final long value) {
        if (value < MIN_VALUE || value > MAX_VALUE || value % VALUE_UNIT != 0) {
            throw new InvalidCouponDiscountAmount("할인 금액이 올바르지 않습니다.");
        }
    }
}
