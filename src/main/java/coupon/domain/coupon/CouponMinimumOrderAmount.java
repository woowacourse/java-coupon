package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class CouponMinimumOrderAmount {

    private static final int MINIMUM_LIMIT = 5000;
    private static final int MAXIMUM_LIMIT = 100000;

    private final int value;

    public CouponMinimumOrderAmount(final int value) {
        validateMinimumOrderAmountLimit(value);
        this.value = value;
    }

    private void validateMinimumOrderAmountLimit(final int value) {
        if (value < MINIMUM_LIMIT || value > MAXIMUM_LIMIT) {
            throw new IllegalArgumentException(
                    "최소 주문 금액은 " + MINIMUM_LIMIT + "원 이상, " + MAXIMUM_LIMIT + "원 이하만 가능합니다. - " + value);
        }
    }
}
