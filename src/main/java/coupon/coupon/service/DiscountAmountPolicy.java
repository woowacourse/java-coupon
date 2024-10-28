package coupon.coupon.service;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.ExceptionMessage;
import org.springframework.stereotype.Component;

@Component
public class DiscountAmountPolicy implements DiscountPolicy {

    public static final int MIN_DISCOUNT_AMOUNT = 1_000;
    public static final int MAX_DISCOUNT_AMOUNT = 10_000;
    public static final int DISCOUNT_AMOUNT_UNIT = 500;

    @Override
    public void validatePolicy(int discountAmount, int minimumOrderAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format(ExceptionMessage.DISCOUNT_AMOUNT_EXCEPTION.getMessage(),
                    MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT, DISCOUNT_AMOUNT_UNIT));
        }
    }
}
