package coupon.coupon.service;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.ExceptionMessage;
import org.springframework.stereotype.Component;

@Component
public class MinimumOrderAmountPolicy implements DiscountPolicy {

    public static final int MIN_MINIMUM_ORDER_AMOUNT = 5_000;
    public static final int MAX_MINIMUM_ORDER_AMOUNT = 100_000;

    @Override
    public void validatePolicy(int discountAmount, int minimumOrderAmount) {
        if (minimumOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format(
                    ExceptionMessage.MINIMUM_ORDER_AMOUNT_EXCEPTION.getMessage(),
                    MIN_MINIMUM_ORDER_AMOUNT,
                    MAX_MINIMUM_ORDER_AMOUNT
            ));
        }
    }
}
