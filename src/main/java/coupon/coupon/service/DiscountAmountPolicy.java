package coupon.coupon.service;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.DiscountPolicyViolationException;
import org.springframework.stereotype.Component;

@Component
public class DiscountAmountPolicy implements DiscountPolicy {

    private static final int MIN_FIXED_AMOUNT = 1_000;
    private static final int MAX_FIXED_AMOUNT = 10_000;
    private static final int FIXED_AMOUNT_UNIT = 500;

    @Override
    public void validatePolicy(long discountAmount, long minimumOrderPrice) {
        if (discountAmount < MIN_FIXED_AMOUNT || discountAmount > MAX_FIXED_AMOUNT) {
            throw new DiscountPolicyViolationException("할인 금액은 " + MIN_FIXED_AMOUNT + "원 이상 " + MAX_FIXED_AMOUNT + "원 이하여야 합니다.");
        }
        if (discountAmount % FIXED_AMOUNT_UNIT != 0) {
            throw new DiscountPolicyViolationException("할인 금액은 " + FIXED_AMOUNT_UNIT + "원 단위로 입력되어야 합니다.");
        }
    }
}
