package coupon.coupon.service;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.ExceptionMessage;
import org.springframework.stereotype.Component;

@Component
public class DiscountRatePolicy implements DiscountPolicy {

    static final int MIN_DISCOUNT_RATE = 3;
    static final int MAX_DISCOUNT_RATE = 20;

    @Override
    public void validatePolicy(int discountAmount, int minimumOrderAmount) {
        double discountRate = Math.floor((double) discountAmount / minimumOrderAmount * 100);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(String.format(ExceptionMessage.DISCOUNT_RATE_EXCEPTION.getMessage(),
                    MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }
}
