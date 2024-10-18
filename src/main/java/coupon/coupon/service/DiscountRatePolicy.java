package coupon.coupon.service;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.DiscountPolicyViolationException;
import org.springframework.stereotype.Component;

@Component
public class DiscountRatePolicy implements DiscountPolicy {

    private static final int MIN_RATE = 3;
    private static final int MAX_RATE = 20;

    @Override
    public void validatePolicy(long fixedDiscountAmount, long minimumOrderPrice) {
        long discountRate = getFlooredDiscountRate(fixedDiscountAmount, minimumOrderPrice);
        if (discountRate < MIN_RATE || discountRate > MAX_RATE) {
            throw new DiscountPolicyViolationException("할인율은 " + MIN_RATE + " 이상 " + MAX_RATE + " 이하여야 합니다.");
        }
    }

    private long getFlooredDiscountRate(long fixedDiscountAmount, long minimumOrderPrice) {
        return (fixedDiscountAmount * 100) / minimumOrderPrice;
    }
}
